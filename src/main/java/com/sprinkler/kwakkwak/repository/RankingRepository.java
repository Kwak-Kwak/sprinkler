package com.sprinkler.kwakkwak.repository;


import com.sprinkler.kwakkwak.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    // usercode로 ranking 찾기
    Optional<Ranking> findByUserInfo_Code(Long code);

    // 90일 랭킹 탑 5
    List<Ranking> findTop5ByOrderByMonthScoreDesc();

    // 일주일 랭킹 탑 5
    List<Ranking> findTop5ByOrderByWeekScoreDesc();

    // 90일 랭킹 전체 출력
    List<Ranking> findByOrderByMonthScoreDesc();

    // 일주일 랭킹 전체 출력
    List<Ranking> findByOrderByWeekScoreDesc();
}

