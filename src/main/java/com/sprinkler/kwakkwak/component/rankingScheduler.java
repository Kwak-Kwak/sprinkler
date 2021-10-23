package com.sprinkler.kwakkwak.component;

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
    @Scheduled(cron = "0 0/5 * * * *")
    public void data_save() {

        System.out.println("data_save");

        List<UserInfo> userInfoList = userInfoRepository.findAll();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DATE, -1);
        String base = SDF.format(calendar.getTime());

        for (UserInfo userInfo : userInfoList) {
            Optional<Ranking> rankingOptional = rankingRepository.findByUserInfo_Code(userInfo.getCode());

            Ranking ranking = null;
            // 생성
            if (rankingOptional.isEmpty()) {
                ranking = Ranking.builder()
                        .month_commit(0L)
                        .month_score(0L)
                        .userInfo(userInfo)
                        .week_commit(0L)
                        .week_score(0L)
                        .build();
            }else{
                ranking = rankingOptional.get();
            }

            // 업데이트

            long score = 0L;
            long commit_num = 0L;

            String[] split = profileRepository.findByUserInfo_Code(userInfo.getCode()).get().getGithub().split("github.com/");
//            String user_name = split[1];

            String user_name = "kwonchanmi";
            JSONArray ret_one = get("/users/" + user_name + "/events?per_page=100&page=1");
            JSONArray ret_two = get("/users/" + user_name + "/events?per_page=100&page=2");
            JSONArray ret_three = get("/users/" + user_name + "/events?per_page=100&page=3");


            int sum = 0;

            for (int i = 0; i < ret_one.length(); i++) {
                JSONObject jsonObject = ret_one.getJSONObject(i);

                String date = jsonObject.get("created_at").toString().split("T")[0];

//                if(!date.equals(base))
//                    continue;

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

                    for (int idx = 0; idx < commits.length(); idx++) {
                        JSONObject author = (JSONObject) commits.getJSONObject(idx).get("author");

                        if (author.get("name").toString().equals(user_name)) {
                            score += 5L;
                            commit_num++;
                        }
                    }

                } else if (type.equals("ReleaseEvent")) {
                    score += 10L;
                } else if (type.equals("SponsorshipEvent")) {
                    score += 5L;
                }

            }

        }


        // month

        // days
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
