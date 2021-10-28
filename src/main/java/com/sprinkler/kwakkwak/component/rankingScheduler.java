package com.sprinkler.kwakkwak.component;

import com.sprinkler.kwakkwak.domain.Profile;
import com.sprinkler.kwakkwak.domain.Ranking;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.repository.ProfileRepository;
import com.sprinkler.kwakkwak.repository.RankingRepository;
import com.sprinkler.kwakkwak.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Component
public class rankingScheduler {

    private final UserInfoRepository userInfoRepository;
    private final RankingRepository rankingRepository;
    private final ProfileRepository profileRepository;

    private final static Logger log = Logger.getGlobal();
    private final static String baseURL="https://api.github.com";


    // 현재로 부터 일주일 전, 현재로부터 90일 전 데이터 적립

    // 매일 아침 10시마다 작동
    @Scheduled(cron = "0 0 10 * * *")
    public void data_save() throws ParseException {


        List<UserInfo> userInfoList = userInfoRepository.findAll();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DATE, -7);
        String week_base = SDF.format(calendar.getTime());

        calendar.add(Calendar.DATE, -90);
        String month_base = SDF.format(calendar.getTime());
        Date month = calendar.getTime();

        for (UserInfo userInfo : userInfoList) {
            Optional<Ranking> rankingOptional = rankingRepository.findByUserInfo_Code(userInfo.getCode());

            Ranking ranking = null;
            // 생성
            if (rankingOptional.isEmpty()) {
                ranking = Ranking.builder()
                        .monthScore(0L)
                        .monthCommit(0L)
                        .weekScore(0L)
                        .weekCommit(0L)
                        .build();
            }else{
                ranking = rankingOptional.get();
            }

            Profile profile = profileRepository.findByUserInfo_Code(userInfo.getCode()).get();

            System.out.println(profile.getGithub());

            String githubUrl = profileRepository.findByUserInfo_Code(userInfo.getCode()).get().getGithub();

            if (githubUrl == null) {
                continue;
            }

            String user_name = githubUrl.split("github.com/")[1];

            JSONArray ret_one = get("/users/" + user_name + "/events?per_page=100&page=1");
            JSONArray ret_two = get("/users/" + user_name + "/events?per_page=100&page=2");
            JSONArray ret_three = get("/users/" + user_name + "/events?per_page=100&page=3");

            Long week_commit = 0L;
            Long week_score = 0L;
            Long month_commit = 0L;
            Long month_score = 0L;



            Long[] week_one = getScore(ret_one, week_base);
            Long[] week_two = getScore(ret_two, week_base);
            Long[] week_three = getScore(ret_three, week_base);

            week_score = week_one[0] + week_two[0] + week_three[0];
            week_commit = week_one[1] + week_two[1] + week_three[1];

            Long[] month_one = getScore(ret_one, month_base);
            Long[] month_two = getScore(ret_two, month_base);
            Long[] month_three = getScore(ret_three, month_base);

            month_score = month_one[0] + month_two[0] + month_three[0];
            month_commit = month_one[1] + month_two[1] + month_three[1];


            System.out.println(week_commit+" "+month_commit);

            if (month_three[2] == 99) {

                System.out.println("max");
                JSONObject object = ret_three.getJSONObject(100);

                String max=object.get("created_at").toString().split("T")[0];
                Date max_date = SDF.parse(max);

                long days = (max_date.getTime() - month.getTime()) / (24 * 60 * 60 * 1000);


                days = Math.abs(days);

                month_score += 200 * days;
                month_commit += 10 * days;


            }

            ranking.update(month_score, week_score, month_commit, week_commit);

            rankingRepository.save(ranking);
        }


    }

    // jsonobject payload에 따른 점수 환산
    public Long[] getScore(JSONArray input, String base){

        long idx = 0L;
        long score = 0L;
        long commit_num = 0L;

        for (int i = 0; i < input.length(); i++) {
            JSONObject jsonObject = input.getJSONObject(i);

            String date = jsonObject.get("created_at").toString().split("T")[0];

            if(date.compareTo(base)<0)
                break;

            String type = jsonObject.get("type").toString();

            JSONObject payload = (JSONObject) jsonObject.get("payload");

            if(type.equals("watchEvent")){
                score += 5L;
            } else if (type.equals("CommitCommentEvent")) {
                score += 10L;
            } else if (type.equals("CreateEvent")) {
                String ref_type = payload.get("ref_type").toString();

                if(ref_type.equals("branch"))
                    score += 10L;
                else if(ref_type.equals("tag"))
                    score+=5L;
                else if(ref_type.equals("repository"))
                    score+=30L;
            } else if (type.equals("DeleteEvent")) {
                score += 0L;
            } else if (type.equals("ForkEvent")) {
                score += 20L;
            } else if (type.equals("GollumEvent")) {
                score += 10L;
            } else if (type.equals("IssueCommentEvent")) {
                String action = payload.get("action").toString();

                if (action.equals("created")) {
                    score += 10L;
                } else if (action.equals("edited")) {
                    score += 5L;
                } else if (action.equals("deleted")) {
                    score += 0L;
                }
            } else if (type.equals("IssuesEvent")) {
                String action = payload.get("action").toString();

                if (action.equals("opened")) {
                    score += 10L;
                } else if (action.equals("closed")) {
                    score += 0L;
                } else if (action.equals("reopened")) {
                    score += 0L;
                } else if (action.equals("assigned")) {
                    score += 5L;
                } else if (action.equals("unassigned")) {
                    score += 0L;
                } else if (action.equals("labled")) {
                    score += 0L;
                } else if (action.equals("unlabled")) {
                    score += 0L;
                }
            } else if (type.equals("PublicEvent")) {
                score += 5L;
            } else if (type.equals("PullRequestEvent")) {
                String action = payload.get("action").toString();

                if (action.equals("opened")) {
                    score += 10L;
                } else if (action.equals("closed")) {
                    score += 0L;
                } else if (action.equals("reopened")) {
                    score += 0L;
                } else if (action.equals("assigned")) {
                    score += 5L;
                } else if (action.equals("unassigned")) {
                    score += 0L;
                } else if (action.equals("review_requested")) {
                    score += 5L;
                } else if (action.equals("review_request_removed")) {
                    score += 0L;
                } else if (action.equals("labled")) {
                    score += 0L;
                } else if (action.equals("unlabled")) {
                    score += 0L;
                } else if (action.equals("synchronize")) {
                    score += 0L;
                }

            } else if (type.equals("PullRequestReviewEvent")) {
                score += 10L;
            } else if (type.equals("PullRequestReviewCommentEvent")) {
                score += 10L;
            } else if (type.equals("PushEvent")) {
                score += 10L;

                JSONArray commits = (JSONArray) payload.get("commits");

                int length = commits.length();

                score += 5L * length;
                commit_num += length;


            } else if (type.equals("ReleaseEvent")) {
                score += 10L;
            } else if (type.equals("SponsorshipEvent")) {
                score += 5L;
            }

            idx++;

        }

        Long[] ret=new Long[3];
        ret[0]=score;
        ret[1]=commit_num;
        ret[2] = idx;

        return ret;
    }


    public JSONArray get(String link) {
        JSONArray ret = new JSONArray();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(baseURL + link);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ret = readJson(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }


    public static JSONArray readJson(HttpURLConnection conn) {

        JSONArray ret = new JSONArray();

        try {
            int responseCode = conn.getResponseCode();

            if (responseCode == 400) {
                log.warning("BAD REQUEST");
            } else if (responseCode == 401) {
                log.warning("UNAUTHORIZED");
            } else if (responseCode == 500) {
                log.warning("SERVER ERROR");
            } else if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = "";

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                String temp = sb.toString();


                ret = new JSONArray(temp);

            } else {
                log.warning("ERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
