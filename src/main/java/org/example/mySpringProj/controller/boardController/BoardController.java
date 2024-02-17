package org.example.mySpringProj.controller.boardController;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardUpdateRequestDTO;
import org.example.mySpringProj.service.boardService.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody BoardRequestDTO boardRequestDTO) {

        boardService.save(boardRequestDTO);

        return new ResponseEntity<>("작성 완료", HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> selectBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.selectBoard(boardId));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDTO> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequestDTO boardUpdateRequestDTO, Authentication authentication) {
        String userName = authentication.getName();
        boardService.updateBoard(boardId,boardUpdateRequestDTO,userName);
        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.OK)
                .successContent("해당 게시물이 수정되었습니다.")
                .Data(null)
                .build());
    }


}
