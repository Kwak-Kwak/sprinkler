package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.domain.Comment;
import com.sprinkler.kwakkwak.domain.Post;
import com.sprinkler.kwakkwak.domain.UserInfo;
import com.sprinkler.kwakkwak.dto.*;
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

    // 전체 커뮤니티
    @GetMapping("/community")
    public String getBoard(Model model) {
        List<PostDto> postList = boardService.getAllPostList();
        model.addAttribute("postList", postList);
        return "community";
    }

    // 스터디 게시판
    @GetMapping("/community/study")
    public String getStudyBoard(Model model) {
        List<PostDto> postList = boardService.getPostList(1L);
        model.addAttribute("postList", postList);
        return "community";
    }

    // 질의응답 게시판
    @GetMapping("/community/question")
    public String getQuestionBoard(Model model) {
        List<PostDto> postList = boardService.getPostList(2L);
        model.addAttribute("postList", postList);
        return "community";
    }

    // 자유 게시판
    @GetMapping("/community/free")
    public String getFreeBoard(Model model) {
        List<PostDto> postList = boardService.getPostList(3L);
        model.addAttribute("postList", postList);
        return "community";
    }

    // 특정 게시물 확인
    @GetMapping("/community/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        PostDetailDto postDetailDto = boardService.getPost(postId);
        List<CommentDto> comment = boardService.getComment(postId);
        model.addAttribute("post", postDetailDto);
        model.addAttribute("commentList", comment);
        return "view";
    }


    @GetMapping("/community/write")
    public String write(@AuthenticationPrincipal UserInfo userInfo) {

        if(userInfo==null)
            return "login";

        return "write";
    }

    @PostMapping("/community/write")
    public RedirectView savePost(CreatePostRequest request,@AuthenticationPrincipal UserInfo userInfo) {
        boardService.savePost(request,userInfo);
        return new RedirectView("/community");
    }

    @PostMapping("/community/comment/{postId}")
    public RedirectView saveComment(@PathVariable Long postId, CreateCommentRequest request, @AuthenticationPrincipal UserInfo userInfo) {

        if(userInfo==null)
            return new RedirectView("/login");

        boardService.saveComment(postId,request,userInfo);
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
