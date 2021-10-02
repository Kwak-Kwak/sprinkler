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

    private Long boardId;

    private String title;

    private String context;


    public CreatePostRequest(Long boardId, String title, String context) {
        this.boardId = boardId;
        this.title = title;
        this.context = context;
    }
}
