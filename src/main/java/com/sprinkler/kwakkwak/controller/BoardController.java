package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.domain.Comment;
import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/community/study/{postId}")
    public String getStudyPost(@PathVariable String postId, Model model) {
        Long id = Long.parseLong(postId);
        Optional<Post> post = boardService.getPost(id);
        List<Comment> comment = boardService.getComment(id);
        model.addAttribute("post", post.get());
        model.addAttribute("commentList", comment);
        return "view";
    }

    @GetMapping("/community/question/{postId}")
    public String getQuestionPost(@PathVariable String postId, Model model) {
        Long id = Long.parseLong(postId);
        Optional<Post> post = boardService.getPost(id);
        List<Comment> comment = boardService.getComment(id);
        model.addAttribute("post", post.get());
        model.addAttribute("commentList", comment);
        return "view";
    }


    @GetMapping("/community/free/{postId}")
    public String getFreePost(@PathVariable String postId, Model model) {
        Long id = Long.parseLong(postId);
        Optional<Post> post = boardService.getPost(id);
        List<Comment> comment = boardService.getComment(id);
        model.addAttribute("post", post.get());
        model.addAttribute("commentList", comment);
        return "view";
    }

    @GetMapping("/community/write")
    public String write() {
        return "write";
    }

    @PostMapping("/community/write")
    public String savePost(@RequestBody CreatePostRequest request) {
        boardService.savePost(request);
        return "redirect:/";
    }



}
