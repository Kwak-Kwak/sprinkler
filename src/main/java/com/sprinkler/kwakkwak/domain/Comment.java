package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long boardId;

    @ManyToOne
    @JoinColumn(name="user_info_code")
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
