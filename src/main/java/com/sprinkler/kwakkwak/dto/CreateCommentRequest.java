package com.sprinkler.kwakkwak.dto;

import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class CreateCommentRequest {

    private String context;
}
