package com.sprinkler.kwakkwak.domain;

import javax.persistence.*;

@Entity
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long boardId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    private String context;
}
