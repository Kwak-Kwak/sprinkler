package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userInfo_code")
    private UserInfo userInfo;

    private Long boardId;

    private String title;

    private String context;

    @Builder
    public Post(UserInfo userInfo,Long boardId, String title, String context) {
        this.userInfo = userInfo;
        this.boardId = boardId;
        this.title = title;
        this.context = context;
    }

}
