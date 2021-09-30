package com.sprinkler.kwakkwak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter

public class SeoultechNotice {

    private int postNumber; // 게시글 번호

    private String postTitle; // 게시글 제목

    private String postWriter; // 게시글 작성자

    private String postDate; // 게시글 작성 날짜

    private int postViewNumber; // 게시글 조회수
}
