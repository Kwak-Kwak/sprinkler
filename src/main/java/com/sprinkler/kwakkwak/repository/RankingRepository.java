package com.sprinkler.kwakkwak.repository;


import com.sprinkler.kwakkwak.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByUserInfo_Code(Long code);
}

