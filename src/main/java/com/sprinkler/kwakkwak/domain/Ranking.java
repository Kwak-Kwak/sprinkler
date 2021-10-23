package com.sprinkler.kwakkwak.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_info_code")
    private UserInfo userInfo;

    private Long month_score;

    private Long month_commit;

    private Long week_score;

    private Long week_commit;

    @Builder
    public Ranking(UserInfo userInfo, Long month_score, Long week_score, Long month_commit, Long week_commit) {
        this.userInfo = userInfo;
        this.month_score = month_score;
        this.week_score = week_score;
        this.month_commit = month_commit;
        this.week_commit = week_commit;
    }
}
