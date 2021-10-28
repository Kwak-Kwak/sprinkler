package com.sprinkler.kwakkwak.controller;

import com.sprinkler.kwakkwak.dto.ProfileDto;
import com.sprinkler.kwakkwak.dto.UserInfoDto;
import com.sprinkler.kwakkwak.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        return "profile";
    }

    // 프로필 등록
    @PostMapping("/profile")
    public String registerProfile(@Valid ProfileDto profileDto, BindingResult errors, Model model) {


        Map<String, String> validatorResult = profileService.validateHandling(errors, profileDto.getUserName());

        if (!validatorResult.isEmpty()) {
            model.addAttribute("profileDto", profileDto);

            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "/profile";
        }

        profileService.saveProfile(profileDto);

        return "/login";
    }

}

