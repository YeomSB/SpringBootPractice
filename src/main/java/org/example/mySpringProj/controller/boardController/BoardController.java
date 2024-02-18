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
    public ResponseEntity<String> write(@RequestBody BoardRequestDTO boardRequestDTO) {

        boardService.save(boardRequestDTO);

        return new ResponseEntity<>("작성 완료", HttpStatus.OK);
    }

    @GetMapping("/view/boardId/{boardId}")
    public ResponseEntity<BoardResponseDTO> selectBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.selectBoard(boardId));
    }

    @PatchMapping("/modify/boardId/{boardId}")
    public ResponseEntity<ResponseDTO> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequestDTO boardUpdateRequestDTO, Authentication authentication) {
        String userName = authentication.getName();
        boardService.updateBoard(boardId,boardUpdateRequestDTO,userName);
        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("해당 게시물이 수정되었습니다.")
                .Data(null)
                .build());
    }

    @DeleteMapping("/delete/boardId/{boardId}")
    public ResponseEntity<ResponseDTO> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("해당 게시물이 삭제되었습니다.")
                .Data(null)
                .build());
    }

    @GetMapping("/list/nickName/{userNickName}")
    public ResponseEntity<List<BoardResponseDTO>> selectBoardsByUserId(@PathVariable String userNickName){
        return ResponseEntity.ok(boardService.selectBoardsByNickName(userNickName));
    }

    @GetMapping("/list/categoryId/{categoryId}")
    public ResponseEntity<List<BoardResponseDTO>> getBoardsList(@PathVariable Long categoryId){
        return ResponseEntity.ok(boardService.getBoardsList(categoryId));
    }

}
