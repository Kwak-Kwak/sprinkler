package com.sprinkler.kwakkwak.controller;

import com.sprinkler.kwakkwak.dto.SeoultechNotice;
import com.sprinkler.kwakkwak.service.SeoultechNoticeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SeoultechNoticeController {

    private final SeoultechNoticeDataService seoultechNoticeDataService;

   @GetMapping("/seoultech")
    public String seoultech(Model model) throws IOException {

        List<SeoultechNotice> seoultechNoticeList = seoultechNoticeDataService.getSeoultechNoticeDatas();

        model.addAttribute("seoultechNotice", seoultechNoticeList);

        return "seoultech";
    }
}
