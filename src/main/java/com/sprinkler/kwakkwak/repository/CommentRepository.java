package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
