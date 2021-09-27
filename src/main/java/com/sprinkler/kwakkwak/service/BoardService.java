package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.Comment;
import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.repository.CommentRepository;
import com.sprinkler.kwakkwak.repository.PostRepository;
import com.sprinkler.kwakkwak.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final UserInfoRepository userInfoRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public List<Post> getPostList(Long boardId) {
        // TODO : 올바른 boardId인지
        List<Post> post = postRepository.findByBoardId(boardId);
        return post;
    }

    @Transactional
    public void savePost(Long boardId, CreatePostRequest request) {
        Optional<UserInfo> userInfo = userInfoRepository.findById(request.getUserInfoCode());

        if (userInfo.isPresent()) {
            Post post = Post.builder()
                    .boardId(boardId)
                    .context(request.getContext())
                    .title(request.getTitle())
                    .userInfo(userInfo.get()).build();

            postRepository.save(post);
        } else {
            // TODO : 에러 처리
        }
    }

    @Transactional
    public void saveComment(Long boardId, Long postId, CreateCommentRequest request) {
        Optional<UserInfo> userInfo = userInfoRepository.findById(request.getUserInfoCode());
        Optional<Post> post = postRepository.findById(postId);

        if (userInfo.isPresent() && post.isPresent()) {
            Comment comment = Comment.builder()
                    .boardId(boardId)
                    .context(request.getContext())
                    .post(post.get())
                    .userinfo(userInfo.get())
                    .build();
            commentRepository.save(comment);

        } else {
            // TODO : 에러 처리
        }
    }
}
