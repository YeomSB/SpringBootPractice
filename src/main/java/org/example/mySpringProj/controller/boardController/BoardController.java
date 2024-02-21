package org.example.mySpringProj.controller.boardController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardUpdateRequestDTO;
import org.example.mySpringProj.service.boardService.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/post")
    public ResponseDTO write(@RequestBody BoardRequestDTO boardRequestDTO,Authentication authentication) {
        boardService.save(boardRequestDTO,authentication.getName());
        return ResponseDTO.success(HttpStatus.CREATED,"게시물 작성 완료",boardRequestDTO);
    }

    @GetMapping("/view/{categoryID}/{boardId}")
    public ResponseEntity<BoardResponseDTO> selectBoard(@PathVariable Long categoryID, @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.selectBoard(boardId));
    }

    @PatchMapping("/modify/{boardId}")
    public ResponseDTO updateBoard( @PathVariable Long boardId, @RequestBody BoardUpdateRequestDTO boardUpdateRequestDTO, Authentication authentication) {
        String userName = authentication.getName();
        boardService.updateBoard(boardId,boardUpdateRequestDTO,userName);
        return ResponseDTO.success(HttpStatus.OK,"게시물 수정 완료",null);
    }

    @DeleteMapping("/delete/{categoryID}/{boardId}")
    public ResponseDTO deleteBoard(@PathVariable Long categoryID, @PathVariable Long boardId,Authentication authentication) {
        boardService.deleteBoard(categoryID,boardId,authentication.getName());
        return ResponseDTO.success(HttpStatus.OK,"게시물 삭제 완료",null);
    }

    @GetMapping("/list/myBoard/{userNickName}")
    public ResponseEntity<List<BoardResponseDTO>> selectBoardsByNickName(@PathVariable String userNickName){
        return ResponseEntity.ok(boardService.selectBoardsByNickName(userNickName));
    }

    @GetMapping("/list/categoryBoard/{categoryId}")
    public ResponseEntity<List<BoardResponseDTO>> getBoardsList(@PathVariable Long categoryId){
        return ResponseEntity.ok(boardService.getBoardsList(categoryId));
    }

}
