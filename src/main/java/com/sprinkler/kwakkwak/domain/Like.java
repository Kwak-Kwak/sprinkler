package com.sprinkler.kwakkwak.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="userInfo_code")
    private UserInfo userInfo;

    @Builder
    public Like(Post post, UserInfo userInfo) {
        this.post = post;
        this.userInfo = userInfo;
    }

}
