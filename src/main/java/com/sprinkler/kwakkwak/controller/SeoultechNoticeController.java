package com.sprinkler.kwakkwak.controller;

import com.sprinkler.kwakkwak.domain.Notice;
import com.sprinkler.kwakkwak.dto.SeoultechNotice;
import com.sprinkler.kwakkwak.service.SeoultechNoticeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SeoultechNoticeController {

    private final SeoultechNoticeDataService seoultechNoticeDataService;

    // 전체 RSS 페이지 출력
    @GetMapping("/seoultech")
    public String seoultech(Model model) throws IOException {

        List<Notice> seoultechNoticeList = seoultechNoticeDataService.getSeoultechNoticeDatas();

        model.addAttribute("seoultechNotice", seoultechNoticeList);

        return "info";
    }

    // 학사공지로 이동
    @GetMapping("/seoultech_post")
    public RedirectView movePost(@RequestParam("id") Long postId) {
        System.out.println("seoultech_post");
        RedirectView redirectView = new RedirectView();
        String url = seoultechNoticeDataService.getPostUrl(postId);
        redirectView.setUrl(url);
        return redirectView;
    }
}
