package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserInfoAndPost(Long userInfoId, Long postId);
}
