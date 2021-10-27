package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Profile;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.ProfileDto;
import com.sprinkler.kwakkwak.repository.ProfileRepository;
import com.sprinkler.kwakkwak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    //프로필 저장
    public Long saveProfile(ProfileDto requestDto) {
        UserInfo userInfo = userRepository.findById(requestDto.getUserCode()).get();

        Profile profile = Profile.builder()
                .blog(requestDto.getBlog())
                .self(requestDto.getSelf())
                .github(requestDto.getGithub())
                .userInfo(userInfo)
                .userName(requestDto.getUserName()).build();

        profileRepository.save(profile);

        return profile.getId();
    }

    // 유효성 체크
    public Map<String, String> validateHandling(Errors errors, String userName) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        if (duplicateMember(userName)) {
            validatorResult.put("valid_userName", "이미 가입된 닉네임입니다.");
        }

        return validatorResult;
    }

    // 중복 닉네임 확인
    public boolean duplicateMember(String userName) {

        return profileRepository.existsByUserName(userName);
    }
}
