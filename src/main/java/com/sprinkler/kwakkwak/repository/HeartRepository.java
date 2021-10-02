package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserInfoAndPost(Long userInfoId, Long postId);
}
