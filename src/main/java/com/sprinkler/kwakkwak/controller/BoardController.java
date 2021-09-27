package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{boardId}/post")
    public String savePost(@PathVariable Long boardId, @RequestBody CreatePostRequest request) {
        boardService.savePost(boardId,request);
        return "redirect:/";
    }

    @GetMapping("/{boardId}")
    public String getPost(@PathVariable Long boardId, Model model) {
        List<Post> postList = boardService.getPostList(boardId);
        model.addAttribute("postList", postList);
        return "board";
    }

    @PostMapping("/{boardId}/{postId}/comment")
    public String saveComment(@PathVariable Long boardId, @PathVariable Long postId, @RequestBody CreateCommentRequest request) {
        boardService.saveComment(boardId, postId, request);
        return "redirect:/";
    }



}
