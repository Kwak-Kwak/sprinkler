package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.domain.Comment;
import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.dto.CreateCommentRequest;
import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        List<Post> postList = boardService.getPostList(boardId);
        model.addAttribute("postList", postList);
        return "/{boardId}";
    }

    @GetMapping("/{boardId}/{postId}")
    public String getPost(@PathVariable Long boardId, @PathVariable Long postId,Model model) {
        Optional<Post> post = boardService.getPost(postId);
        List<Comment> comment = boardService.getComment(postId);

        if (post.isPresent()) {
            model.addAttribute("post", post);
            model.addAttribute("commentList", comment);
        }


        return "redirect:/{boardId}/{postId}";
    }

    @PostMapping("/{boardId}/post")
    public String savePost(@PathVariable Long boardId, @RequestBody CreatePostRequest request) {
        boardService.savePost(boardId,request);
        return "redirect:/{boardId}";
    }

    @DeleteMapping("/{boardId}/{postId}/{userId}")
    public String deletePost(@PathVariable Long boardId,@PathVariable Long postId, @PathVariable Long userId) {
        boardService.deletePost(postId, userId);
        return "redirect:/{boardId}";
    }

//    @PatchMapping("/{boardId}/{postId}/")
//    public String updatePost(@PathVariable Long boardId, @PathVariable Long postId) {
//        // TODO : 게시물 수정
//    }


    @PostMapping("/{boardId}/{postId}/comment")
    public String saveComment(@PathVariable Long boardId, @PathVariable Long postId, @RequestBody CreateCommentRequest request) {
        boardService.saveComment(boardId, postId, request);
        return "redirect:/{boardId}/{postId}";
    }

    @DeleteMapping("/{boardId}/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long postId, @PathVariable Long commentId) {
        boardService.deleteComment(commentId);
        return "redirect:/{boardId}/{postId}";
    }

//    @PatchMapping("/{boardId}/{postId}/")
//    public String updateComment(@PathVariable Long boardId, @PathVariable Long postId) {
//        // TODO : 게시물 수정
//    }

    @PostMapping("/{boardId}/{postId}/like/{userId}")
    public String saveLike(@PathVariable Long postId, @PathVariable Long userId ) {

        boardService.saveLike(postId,userId);
        return "redirect:/";
    }

    @GetMapping("/{boardId}/{postId}/like/{userId}")
    public String getLike(@PathVariable Long postId, @PathVariable Long userId,Model model) {

        model.addAttribute("like_status", boardService.getLike(postId, userId));

        return "redirect:/{boardId}/{postId}";
    }

    @DeleteMapping("/{boardId}/{postId}/like/{userId}")
    public String deleteLike(@PathVariable Long postId, @PathVariable Long userId) {

        boardService.deleteLike(postId, userId);

        return "redirect:/{boardId}/{postId}/like";
    }

    @PostMapping("/{boardId}/{postId}/scrap/{userId}")
    public String saveScrap(@PathVariable Long postId, @PathVariable Long userId) {

        boardService.saveLike(postId,userId);
        return "redirect:/";
    }

    @GetMapping("/{boardId}/{postId}/scrap/{userId}")
    public String getScrap(@PathVariable Long postId, @PathVariable Long userId,Model model) {

        model.addAttribute("scrap_status", boardService.getScrap(postId, userId));

        return "redirect:/{boardId}/{postId}";
    }

    @DeleteMapping("/{boardId}/{postId}/scrap/{userId}")
    public String deleteScrap(@PathVariable Long postId, @PathVariable Long userId) {

        boardService.deleteScrap(postId, userId);

        return "redirect:/{boardId}/{postId}/scrap";
    }



}
