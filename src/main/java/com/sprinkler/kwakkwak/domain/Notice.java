package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Notice {

    @Id
    private Long postId; // 게시글 번호

    private String postTitle; // 게시글 제목

    private String postWriter; // 게시글 작성자

    private String postDate; // 게시글 작성 날짜

    private int postViewNumber; // 게시글 조회수

    private String postUrl; // 게시글 URL

    @Builder
    public Notice(Long postId, String postTitle, String postWriter, String postDate, int postViewNumber, String postUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postWriter = postWriter;
        this.postDate = postDate;
        this.postViewNumber = postViewNumber;
        this.postUrl = postUrl;
    }
}
