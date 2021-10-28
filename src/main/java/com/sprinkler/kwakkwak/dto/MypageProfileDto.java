package com.sprinkler.kwakkwak.dto;

import com.sprinkler.kwakkwak.controller.MyPageController;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageProfileDto {

    private String userName;

    private String github;

    private String blog;

    private String self;

    @Builder
    public MypageProfileDto(String userName, String github, String blog, String self) {
        this.userName = userName;
        this.github = github;
        this.blog = blog;
        this.self = self;
    }

}
