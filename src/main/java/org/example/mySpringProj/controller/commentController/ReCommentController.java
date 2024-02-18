package org.example.mySpringProj.controller.commentController;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.commentDto.CommentRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.service.commentService.CommentService;
import org.example.mySpringProj.service.commentService.ReCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reComment")
public class ReCommentController {
    private final ReCommentService reCommentService;

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody CommentRequestDTO commentRequestDTO, Authentication authentication) {
        String userName = authentication.getName();
        reCommentService.save(commentRequestDTO,userName);
        return new ResponseEntity<>("작성 완료", HttpStatus.OK);
    }

    @DeleteMapping("/delete/commentId/{commentId}/recommentID/{recommentId}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long commentId, @PathVariable Long recommentId) {
        reCommentService.deleteReComment(commentId,recommentId);

        return ResponseEntity.ok().body(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("대댓글 삭제 완료")
                .Data(null)
                .build()
        );
    }

    @GetMapping("/list/{commentId}")
    public ResponseEntity<List<CommentResponseDTO>> getReComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(reCommentService.getReComments(commentId));
    }



}
