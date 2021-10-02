package com.sprinkler.kwakkwak.repository;

import com.sprinkler.kwakkwak.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByBoardId(Long boardId);
}
