package com.sprinkler.kwakkwak.controller;


import com.sprinkler.kwakkwak.dto.CreatePostRequest;
import com.sprinkler.kwakkwak.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{boardId}/post")
    public String savePost(@PathVariable Long boardId, @RequestBody CreatePostRequest request) {
        boardService.savePost(boardId,request);
        return "redirect:/";
    }


}
