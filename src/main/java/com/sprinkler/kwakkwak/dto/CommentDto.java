package com.sprinkler.kwakkwak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CommentDto {

    private String userName;

    private LocalDate createdAt;

    private String content;

    @Builder
    public CommentDto(String userName, LocalDate createdAt, String content) {
        this.userName = userName;
        this.createdAt = createdAt;
        this.content = content;
    }
}
