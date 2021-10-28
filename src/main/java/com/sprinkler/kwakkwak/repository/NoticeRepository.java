package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 포스트 아이디로 notice 찾기
    Optional<Notice> findByPostId(Long id);
}
