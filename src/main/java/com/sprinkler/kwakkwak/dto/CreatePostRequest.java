package com.sprinkler.kwakkwak.dto;

import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class CreatePostRequest {

    private Long userInfoCode;

    private String title;

    private String context;


    public CreatePostRequest(Long userInfoCode, String title, String context) {
        this.userInfoCode = userInfoCode;
        this.title = title;
        this.context = context;
    }
}
