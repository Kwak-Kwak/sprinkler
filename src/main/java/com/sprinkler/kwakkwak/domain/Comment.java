package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long boardId;

    @ManyToOne
    @JoinColumn(name="userInfo_code")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    private String context;

    @Builder
    public Comment(Long boardId, UserInfo userinfo, Post post, String context) {
        this.boardId = boardId;
        this.userInfo = userinfo;
        this.post = post;
        this.context = context;
    }
}
