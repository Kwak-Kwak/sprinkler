package com.sprinkler.kwakkwak.domain;

import javax.persistence.*;

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
}
