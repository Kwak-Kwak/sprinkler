package com.sprinkler.kwakkwak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Long postId;

    private String userName;

    private String title;

    private String context;

    private Long commentNum;

    private Long heartNum;

    private LocalDate createdAt;

    @Builder
    public PostDto(Long postId, String userName, String title, String context, Long commentNum, Long heartNum, LocalDate createdAt) {
        this.postId = postId;
        this.userName = userName;
        this.title = title;
        this.context = context;
        this.commentNum = commentNum;
        this.heartNum = heartNum;
        this.createdAt = createdAt;
    }
}
