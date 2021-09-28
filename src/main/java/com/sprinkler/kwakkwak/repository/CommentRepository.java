package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Long postId);
}
