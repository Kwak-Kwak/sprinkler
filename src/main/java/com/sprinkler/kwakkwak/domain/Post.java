package com.sprinkler.kwakkwak.domain;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.*;

@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private Long boardId;

    private String title;

    private String context;
}
