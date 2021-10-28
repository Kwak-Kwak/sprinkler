package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_info_code")
    private UserInfo userInfo;

    private Long monthScore;

    private Long monthCommit;

    private Long weekScore;

    private Long weekCommit;

    @Builder
    public Ranking(UserInfo userInfo, Long monthScore, Long weekScore, Long monthCommit, Long weekCommit) {
        this.userInfo = userInfo;
        this.monthScore = monthScore;
        this.weekScore = weekScore;
        this.monthCommit = monthCommit;
        this.weekCommit = weekCommit;
    }

    public void update(Long monthScore, Long weekScore, Long monthCommit, Long weekCommit){
        this.monthCommit = monthCommit;
        this.weekCommit = weekCommit;
        this.monthScore = monthScore;
        this.weekScore = weekScore;
    }
}
