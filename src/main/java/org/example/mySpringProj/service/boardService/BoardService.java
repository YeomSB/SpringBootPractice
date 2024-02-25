package org.example.mySpringProj.service.boardService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardUpdateRequestDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.categoryRepository.CategoryRepository;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.service.UtilFunc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(BoardRequestDTO boardRequestDTO,String reqName) {
        User user = userRepository.findByUserName(reqName)
                .orElseThrow(() ->  new AppException(ErrorCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다.",reqName));
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
                .categoryName(board.getCategory().getName())
                .nickname(user.getNickName())
                .title(board.getTitle())
                .contents(board.getContents())
                .likes(board.getLikeCount())
                .viewCnt(board.getViewCount())
                .build();
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequestDTO boardUpdateRequestDTO, String userName) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 게시물을 찾을 수 없습니다.",boardId));
        UtilFunc.hasPermission(board.getUser().getUserName(),userName);
        board.setContents(boardUpdateRequestDTO.getContents());

    }

    @Transactional
    public void deleteBoard(Long categoryId, Long boardId,String reqName) {

        UtilFunc.hasPermission(categoryRepository.findById(categoryId).orElseThrow().getUser().getUserName(),reqName);
        UtilFunc.hasPermission(boardRepository.findById(boardId).orElseThrow().getUser().getUserName(),reqName);

        Board boardByComments = boardRepository.findFetchCommentsById(boardId);
        if(boardByComments!=null)
            commentRepository.deleteAllByBoard(boardByComments);

        boardRepository.delete(boardRepository.findById(boardId).orElseThrow());
    }

    public List<BoardResponseDTO> selectBoardsByNickName(String userNickName) {
        User user = userRepository.findByNickName(userNickName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다.", userNickName));

        List<Board> boards = boardRepository.findAllByUser(user);
        return BoardResponseDTO.getDtoList(boards);
    }

    public List<BoardResponseDTO> getBoardsList(Long categoryId){
        List<Board> boards = boardRepository.findAllByCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"해당 게시판을 찾을 수 없습니다",categoryId))
        );
        return BoardResponseDTO.getDtoList(boards);
    }

    private Board getBoard(BoardRequestDTO boardRequestDTO, User findUser) {
        Board board = Board.builder()
                .category(categoryRepository.findById(boardRequestDTO.getCategoryId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"해당 게시판을 찾을 수 없습니다",boardRequestDTO.getCategoryId())))
                .title(boardRequestDTO.getTitle())
                .contents(boardRequestDTO.getContents())
                .user(findUser)
                .build();
        return board;
    }



}
