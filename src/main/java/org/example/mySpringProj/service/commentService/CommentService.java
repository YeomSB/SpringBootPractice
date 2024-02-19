package org.example.mySpringProj.service.commentService;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.commentDto.CommentRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.commentRepository.ReCommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReCommentRepository reCommentRepository;


    @Transactional
    public void save(CommentRequestDTO commentRequestDTO,String userName){
        Board board = checkBoard(commentRequestDTO.getId());
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"",null));

        commentRepository.save(Comment.builder()
                .board(board)
                .contents(commentRequestDTO.getContents())
                .user(user)
                .build());

    }

    public List<CommentResponseDTO> selectBoard(Long board_id) {
        Board board = checkBoard(board_id);

        List<Comment> comments = commentRepository.getAllByBoard(board);
        return addDtoList(comments);
    }

    public List<CommentResponseDTO> selectUser(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 유저가 없습니다.",user_id));

        List<Comment> comments = commentRepository.getAllByUser(user);
        return addDtoList(comments);

    }

    @Transactional
    public void deleteComment(Long board_id, Long comment_id) {
        Board board = checkBoard(board_id);
        Comment comment = checkComment(comment_id);

        if(!reCommentRepository.findALLByComment(comment).isEmpty()) {
            comment.setContents("해당 댓글은 게시자에 의해 삭제되었습니다.");
            comment.setRegdate(null);
            comment.setUser(null);
        }
        else {
        commentRepository.delete(commentRepository.findByIdAndBoard(comment_id,board)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"해당 댓글을 찾을 수 없습니다",comment_id)));
        }
    }

    private List<CommentResponseDTO> addDtoList(List<Comment> comments){
        List<CommentResponseDTO> commentResponseDTOList= new ArrayList<>();
        for(Comment comment : comments){
            commentResponseDTOList.add(
                    CommentResponseDTO.builder()
                            .userNickName(comment.getUser().getNickName())
                            .contents(comment.getContents())
                            .regDate(comment.getRegdate())
                            .build()
            );
        }
        return commentResponseDTOList;
    }

    private Board checkBoard(Long board_id){
        return boardRepository.findById(board_id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 게시물이 없습니다.",board_id));
    }
    private Comment checkComment(Long comment_id){
        return commentRepository.findById(comment_id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 댓글이 없습니다.",comment_id));
    }


}
