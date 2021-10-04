package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.domain.Comment;
import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/community")
    public String getBoard(Model model) {
        List<Post> postList = boardService.getAllPostList();
        model.addAttribute("postList", postList);
        return "community";
    }


    @GetMapping("/community/study")
    public String getStudyBoard(Model model) {
        List<Post> postList = boardService.getPostList(1L);
        model.addAttribute("postList", postList);
        return "community";
    }

    @GetMapping("/community/question")
    public String getQuestionBoard(Model model) {
        List<Post> postList = boardService.getPostList(2L);
        model.addAttribute("postList", postList);
        return "community";
    }

    @GetMapping("/community/free")
    public String getFreeBoard(Model model) {
        List<Post> postList = boardService.getPostList(3L);
        model.addAttribute("postList", postList);
        return "community";
    }

    @GetMapping("/community/{postId}")
    public String getStudyPost(@PathVariable String postId, Model model) {
        Long id = Long.parseLong(postId);
        Optional<Post> post = boardService.getPost(id);
        List<Comment> comment = boardService.getComment(id);
        model.addAttribute("post", post.get());
        model.addAttribute("commentList", comment);
        return "view";
    }


    @GetMapping("/community/write")
    public String write() {
        System.out.println("write");
        return "write";
    }

    @PostMapping("/community/write")
    public RedirectView savePost(CreatePostRequest request,@AuthenticationPrincipal UserInfo userInfo) {
        boardService.savePost(request,userInfo);
        return new RedirectView("/community");
    }

    @PostMapping("/community/{postId}")
    public RedirectView saveComment(@PathVariable String postId, CreateCommentRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        Long id = Long.parseLong(postId);
        boardService.saveComment(id,request,userInfo);
        return new RedirectView("/community/{postId}");
    }

    @DeleteMapping("/community/{postId}/{commentId}")
    public RedirectView deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        Long post_id = Long.parseLong(postId);
        Long comment_id = Long.parseLong(commentId);

        boardService.deleteComment(post_id,comment_id);
        return new RedirectView("/community/{postId}");
    }

    @GetMapping("/community/{postId}/like")
    public RedirectView saveLike(@PathVariable String postId, @AuthenticationPrincipal UserInfo userInfo) {
        Long id = Long.parseLong(postId);
        boardService.saveLike(id, userInfo);
        return new RedirectView("/community/{postId}");
    }

    @DeleteMapping("/community/{postId}/like")
    public RedirectView deleteLike(@PathVariable String postId, @AuthenticationPrincipal UserInfo userInfo) {
        Long id = Long.parseLong(postId);
        boardService.deleteLike(id, userInfo);
        return new RedirectView("/community/{postId}");
    }

    @GetMapping("/community/{postId}/scrap")
    public RedirectView saveScrap(@PathVariable String postId, @AuthenticationPrincipal UserInfo userInfo) {
        Long id = Long.parseLong(postId);
        boardService.saveScrap(id,userInfo);
        return new RedirectView("/community/{postId}");
    }

    @DeleteMapping("/community/{postId}/scrap")
    public RedirectView deleteScrap(@PathVariable String postId, @AuthenticationPrincipal UserInfo userInfo) {
        Long id = Long.parseLong(postId);
        boardService.deleteScrap(id, userInfo);
        return new RedirectView("/community/{postId}");
    }




}
