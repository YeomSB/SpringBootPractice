package org.example.mySpringProj.controller.boardController;

import lombok.RequiredArgsConstructor;

import org.example.mySpringProj.service.likeService.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{boardId}/{userId}")
    public ResponseEntity<String> like(@PathVariable Long boardId, @PathVariable Long userId) {
        return new ResponseEntity<>(likeService.like(boardId, userId), HttpStatus.OK);
    }
}
