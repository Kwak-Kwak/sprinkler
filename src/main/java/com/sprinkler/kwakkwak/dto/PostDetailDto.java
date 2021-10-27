package com.sprinkler.kwakkwak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class PostDetailDto {

    private Long postId;

    private String postTitle;

    private String postContext;

    private Long postCommentNum;

    private LocalDate createdAt;

    private String userName;

    private String self;

    @Builder
    public PostDetailDto(Long postId,String postTitle, String postContext, Long postCommentNum, LocalDate createdAt, String userName, String self) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContext = postContext;
        this.postCommentNum = postCommentNum;
        this.createdAt = createdAt;
        this.userName = userName;
        this.self = self;
    }
}
