package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.dto.SeoultechNotice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service

public class SeoultechNoticeDataService {

    private static String SEOULTECH_NOTICE_DATAS_URL = "https://www.seoultech.ac.kr/service/info/matters/";

    public List<SeoultechNotice> getSeoultechNoticeDatas() throws IOException {

        Document doc = Jsoup.connect(SEOULTECH_NOTICE_DATAS_URL).get();
        List<SeoultechNotice> seoultechNoticeList = new ArrayList<>();
        Elements contents = doc.select("table tbody tr");

        for (Element content : contents) {
            Elements tdContents = content.select("td");

            SeoultechNotice seoultechNotice = SeoultechNotice.builder()
                    .postNumber(Integer.parseInt(tdContents.select("dn1").text()))
                    .postTitle(tdContents.select("tit.dn2").text())
                    .postWriter(tdContents.select("dn4").text())
                    .postDate(tdContents.select("dn5").text())
                    .postViewNumber(Integer.parseInt(tdContents.select("dn6").text()))
                    .build();

            seoultechNoticeList.add(seoultechNotice);
        }

        return seoultechNoticeList;
    }
}
