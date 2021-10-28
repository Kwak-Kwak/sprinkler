package com.sprinkler.kwakkwak.component;

import com.sprinkler.kwakkwak.domain.Notice;
import com.sprinkler.kwakkwak.dto.SeoultechNotice;
import com.sprinkler.kwakkwak.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@RequiredArgsConstructor
@Component
public class RssScheduler {

    private final static String SEOULTECH_NOTICE_DATAS_URL = "https://www.seoultech.ac.kr/service/info/matters/";
    private final NoticeRepository noticeRepository;


    // 매일 오전 12시 1분 실행
    @Scheduled(cron = "0 1 0 * * *")
    public void rss_save() throws IOException {

        System.out.println("rss_save");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DATE, -1);
        String base = SDF.format(calendar.getTime());

        Document doc = Jsoup.connect(SEOULTECH_NOTICE_DATAS_URL).get();
        List<Notice> seoultechNoticeList = new ArrayList<>();
        Elements contents = doc.select("table tbody tr");

        for (Element content : contents) {
            Elements tdContents = content.select("td");


            if (tdContents.get(0).text().equals("")) {
                continue;
            }

            String now = tdContents.get(4).text();

            if(now.compareTo(base)<0)
                break;


            Notice notice = Notice.builder()
                    .postId(Long.parseLong(tdContents.get(0).text()))
                    .postTitle(tdContents.get(1).text())
                    .postUrl(SEOULTECH_NOTICE_DATAS_URL+tdContents.get(1).select("a").attr("href"))
                    .postWriter(tdContents.get(3).text())
                    .postDate(tdContents.get(4).text())
                    .postViewNumber(Integer.parseInt(tdContents.get(5).text()))
                    .build();

            seoultechNoticeList.add(notice);

        }

        noticeRepository.saveAll(seoultechNoticeList);
    }
}
