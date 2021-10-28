package com.sprinkler.kwakkwak.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sprinkler.kwakkwak.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import com.sprinkler.kwakkwak.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user")
    public String signup(@Valid UserInfoDto infoDto, BindingResult errors, Model model) {

        Map<String, String> validatorResult = userService.validateHandling(errors, infoDto.getEmail());

        if (!validatorResult.isEmpty()) {

            model.addAttribute("infoDto", infoDto);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/signup";


        }


        // 회원 추가 -> 프로필 페이지로 이동
        Long code=userService.save(infoDto);

        System.out.println(code);

        model.addAttribute("userCode", code);

        return "/profile";
    }

    // 회원 등록 화면으로 이동
    @GetMapping("/signup")
    public String register(Model model) {
        UserInfoDto userInfoDto = new UserInfoDto();
        model.addAttribute("infoDto", userInfoDto);
        return "signup";
    }

    // 로그아웃
    @GetMapping( "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }


}
