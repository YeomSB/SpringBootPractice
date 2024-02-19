package org.example.mySpringProj.service.boardService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.boardDomain.Likes;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.commentDomain.ReComment;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.boardDto.BoardRequestDTO;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.boardDto.BoardUpdateRequestDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.boardRepository.LikeRepository;
import org.example.mySpringProj.repository.categoryRepository.CategoryRepository;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.commentRepository.ReCommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final ReCommentRepository reCommentRepository;
    private final LikeRepository likeRepository;

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
                .categoryName(board.getCategory().getName())
                .userNickname(user.getNickName())
                .title(board.getTitle())
                .contents(board.getContents())
                .likes(board.getLikes().size())
                .viewCnt(board.getViewCount())
                .build();
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequestDTO boardUpdateRequestDTO, String userName) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 게시물을 찾을 수 없습니다.",boardId));

        if(!board.getUser().getUserName().equals(userName))
            throw new AppException(ErrorCode.BAD_REQUEST,"해당 게시글의 수정 권한이 없습니다.",userName);

        if(boardUpdateRequestDTO.getContents() == null)
            throw new AppException(ErrorCode.BAD_REQUEST,"해당 게시글의 수정 사항이 없습니다.",null);

        board.setContents(boardUpdateRequestDTO.getContents());

    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findFetchLikesById(boardId);
        List<Likes> likes = board.getLikes();
        likeRepository.deleteAll(likes);

        Board board2 = boardRepository.findFetchCommentsById(boardId);
        List<Comment> comments = board2.getComments();
        for (Comment comment : comments) {
            List<ReComment> reComments = comment.getReComments();
            reCommentRepository.deleteAll(reComments);
        }

        commentRepository.deleteAllByBoard(board);
        boardRepository.delete(board);
    }

    public List<BoardResponseDTO> selectBoardsByNickName(String userNickName) {
        User user = userRepository.findByNickName(userNickName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 유저를 찾을 수 없습니다.", userNickName));

        List<Board> boards = boardRepository.findAllByUser(user);
        return getDtoList(boards);
    }

    public List<BoardResponseDTO> getBoardsList(Long categoryId){
        List<Board> boards = boardRepository.findAllByCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"해당 게시판을 찾을 수 없습니다",categoryId))
        );
        List<BoardResponseDTO> boardResponseDTOList = getDtoList(boards);
        return boardResponseDTOList;
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

    private List<BoardResponseDTO> getDtoList(List<Board> boards) {
        List<BoardResponseDTO> boardResponseDTOS = new ArrayList<>();
        for(Board board : boards) {
            boardResponseDTOS.add(
                    BoardResponseDTO.builder()
                            .categoryName(board.getCategory().getName())
                            .boardId(board.getId())
                            .title(board.getTitle())
                            .userNickname(board.getUser().getNickName())
                            .contents(board.getContents())
                            .likes(board.getLikes().size())
                            .viewCnt(board.getViewCount())
                            .build()
            );
        }
        return boardResponseDTOS;
    }

}
