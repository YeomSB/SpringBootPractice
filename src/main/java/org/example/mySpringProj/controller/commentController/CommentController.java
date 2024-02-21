package org.example.mySpringProj.controller.commentController;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.service.commentService.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody CommentRequestDTO commentRequestDTO,Authentication authentication) {
        commentService.save(commentRequestDTO,authentication.getName());
        return new ResponseEntity<>("작성 완료", HttpStatus.OK);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<List<CommentResponseDTO>> selectBoard(@PathVariable Long board_id) {
        return ResponseEntity.ok(commentService.selectBoard(board_id));
    }

    @GetMapping("/my-comment/{user_id}")
    public ResponseEntity<List<CommentResponseDTO>> selectUser(@PathVariable Long user_id) {
        return ResponseEntity.ok(commentService.selectUser(user_id));
    }


    @DeleteMapping("/delete/{board_id}/{comment_id}")
    public ResponseDTO deleteComment(@PathVariable Long board_id,@PathVariable Long comment_id,Authentication authentication) {
        commentService.deleteComment(board_id,comment_id,authentication.getName());
        return ResponseDTO.success(HttpStatus.OK, "댓글 삭제 완료", null);
    }

}
