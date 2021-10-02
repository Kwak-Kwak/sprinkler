package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_info_code")
    private UserInfo userInfo;

    private Long boardId;

    private String title;

    private String context;

    private Long commentNum;

    @Builder
    public Post(UserInfo userInfo,Long boardId, String title, String context, Long commentNum) {
        this.userInfo = userInfo;
        this.boardId = boardId;
        this.title = title;
        this.context = context;
        this.commentNum = commentNum;
    }

    public void update(int amount) {
        commentNum += amount;
    }



}
