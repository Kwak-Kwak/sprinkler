package com.sprinkler.kwakkwak.service;

import com.sprinkler.kwakkwak.domain.*;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.repository.*;
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
    private final HeartRepository heartRepository;
    private final ScrapRepository scrapRepository;

    @Transactional
    public List<Post> getAllPostList() {
        List<Post> post = postRepository.findAll();
        return post;
    }


    @Transactional
    public List<Post> getPostList(Long boardId) {
        List<Post> post = postRepository.findByBoardId(boardId);
        return post;
    }

    @Transactional
    public Optional<Post> getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        return post;
    }

    @Transactional
    public void savePost(CreatePostRequest request,UserInfo userInfo) {

        Post post = Post.builder()
                .boardId(request.getBoardId())
                .context(request.getContext())
                .title(request.getTitle())
                .userInfo(userInfo).build();

        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId,Long userId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<UserInfo> userInfo = userInfoRepository.findById(userId);

        if (post.isPresent()) {
            if (post.get().getUserInfo().equals(userInfo.get())) {
                postRepository.delete(post.get());
            }
        }
    }

    @Transactional
    public List<Comment> getComment(Long postId) {
        List<Comment> comment = commentRepository.findByPost_Id(postId);

        return comment;
    }

    @Transactional
    public void saveComment(Long boardId, Long postId, CreateCommentRequest request) {
//        Optional<UserInfo> userInfo = userInfoRepository.findById(request.getUserInfoCode());
//        Optional<Post> post = postRepository.findById(postId);
//
//        if (userInfo.isPresent() && post.isPresent()) {
//            Comment comment = Comment.builder()
//                    .boardId(boardId)
//                    .context(request.getContext())
//                    .post(post.get())
//                    .userinfo(userInfo.get())
//                    .build();
//            commentRepository.save(comment);
//
//            post.get().update(1);
//
//        } else {
//            // TODO : 에러 처리
//        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
        }
    }

    @Transactional
    public void saveLike(Long postId, UserInfo userInfo) {

        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()) {
            Heart newHeart = Heart.builder()
                    .post(post.get())
                    .userInfo(userInfo)
                    .build();

            heartRepository.save(newHeart);
        }
    }

    @Transactional
    public boolean getLike(Long postId, Long userId) {
        Optional<Heart> like = heartRepository.findByUserInfoAndPost(postId, userId);

        return like.isPresent();
    }

    @Transactional
    public void deleteLike(Long postId, UserInfo userInfo) {
        Optional<Heart> like = heartRepository.findByUserInfoAndPost(postId, userInfo.getCode());

        if (like.isPresent()) {
            heartRepository.delete(like.get());
        }
    }

    @Transactional
    public void saveScrap(Long postId, UserInfo userInfo) {

        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()) {
            Scrap newScrap = Scrap.builder()
                    .post(post.get())
                    .userInfo(userInfo).build();

            scrapRepository.save(newScrap);

        }
    }

    @Transactional
    public boolean getScrap(Long postId, Long userId) {
        Optional<Scrap> scrap = scrapRepository.findByUserInfoAndPost(userId, postId);

        return scrap.isPresent();
    }

    @Transactional
    public void deleteScrap(Long postId, UserInfo userInfo) {
        Optional<Scrap> scrap = scrapRepository.findByUserInfoAndPost(postId, userInfo.getCode());

        if (scrap.isPresent()) {
            scrapRepository.delete(scrap.get());
        }
    }
}
