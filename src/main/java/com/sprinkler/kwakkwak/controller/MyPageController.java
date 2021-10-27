package com.sprinkler.kwakkwak.controller;

import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")

    public String getMyPage(@AuthenticationPrincipal UserInfo userInfo, Model model) {

        if (userInfo == null) {
            return "redirect:/login";
        }

        model.addAttribute("profile", myPageService.getprofile(userInfo));
        model.addAttribute("postList", myPageService.getPostList(userInfo));

        return "mypage";
    }
}
