package org.example.mySpringProj.controller.commentController;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.dto.searchDto.SearchDTO;
import org.example.mySpringProj.service.commentService.CommentService;
import org.example.mySpringProj.service.searchService.SearchService;
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
    private final SearchService searchService;

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody CommentRequestDTO commentRequestDTO,Authentication authentication) {
        commentService.save(commentRequestDTO,authentication.getName());
        return new ResponseEntity<>("작성 완료", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{board_id}/{comment_id}")
    public ResponseDTO deleteComment(@PathVariable Long board_id,@PathVariable Long comment_id,Authentication authentication) {
        commentService.deleteComment(board_id,comment_id,authentication.getName());
        return ResponseDTO.success(HttpStatus.OK, "댓글 삭제 완료", null);
    }

    @GetMapping("/search")
    public ResponseDTO search
            (@RequestParam(required = false) Long userId, @RequestParam(required = false) Long boardId,
             @RequestParam(required = false) String content) {
        SearchDTO dto = new SearchDTO(userId, null, boardId, null, content);
        return ResponseDTO.success(HttpStatus.OK, null, searchService.searchComments(dto));
    }


}
