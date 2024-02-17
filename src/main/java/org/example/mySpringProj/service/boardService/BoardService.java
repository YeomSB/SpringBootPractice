package org.example.mySpringProj.service.boardService;

import lombok.RequiredArgsConstructor;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardUpdateRequestDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(BoardRequestDTO boardRequestDTO) {
        User user = userRepository.findById(boardRequestDTO.getUserId()).orElseThrow();

        Board board = getBoard(boardRequestDTO, user);

        boardRepository.save(board);
    }

    @Transactional
    public BoardResponseDTO selectBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 게시물이 없습니다.",boardId));

        board.updateViewCount();

        User user = userRepository.findById(board.getUser().getId()).orElseThrow();
        return BoardResponseDTO.builder()
                .userNickname(user.getNickName())
                .title(board.getTitle())
                .contents(board.getContents())
                .build();
    }



    public void updateBoard(Long boardId, BoardUpdateRequestDTO boardUpdateRequestDTO, String userName) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 게시물을 찾을 수 없습니다.",boardId));

        if(!board.getUser().getUserName().equals(userName))
            throw new AppException(ErrorCode.BAD_REQUEST,"해당 게시글의 수정 권한이 없습니다.",userName);

        if(boardUpdateRequestDTO.getContents() != null)
            throw new AppException(ErrorCode.BAD_REQUEST,"해당 게시글의 수정 사항이 없습니다.",null);


        board.setContents(boardUpdateRequestDTO.getContents());
        boardRepository.save(board);
    }

    private static Board getBoard(BoardRequestDTO boardRequestDTO, User findUser) {
        Board board = Board.builder()
                .title(boardRequestDTO.getTitle())
                .contents(boardRequestDTO.getContents())
                .user(findUser)
                .build();
        return board;
    }

}
