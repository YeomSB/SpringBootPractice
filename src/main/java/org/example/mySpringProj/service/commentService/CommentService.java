package org.example.mySpringProj.service.commentService;


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
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.service.UtilFunc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void save(CommentRequestDTO commentRequestDTO,String userName){
        commentRepository.save(Comment.builder()
                .board(checkBoard(commentRequestDTO.getBoardId()))
                .contents(commentRequestDTO.getContents())
                .user(userRepository.findByUserName(userName)
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"",null)))
                .parentId(commentRequestDTO.getParentId())
                .build());
    }

    public List<CommentResponseDTO> selectBoard(Long board_id) {
        Board board = checkBoard(board_id);
        return CommentResponseDTO.addDtoList(commentRepository.getAllByBoard(board));
    }

    public List<CommentResponseDTO> selectUser(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 유저가 없습니다.",user_id));
        return CommentResponseDTO.addDtoList(commentRepository.getAllByUser(user));
    }

    @Transactional
    public void deleteComment(Long board_id, Long comment_id,String reqName) {

        UtilFunc.hasPermission(commentRepository.findById(comment_id).orElseThrow().getUser().getUserName(),reqName);

        Board board = checkBoard(board_id);
        Comment comment = checkComment(comment_id);

        if(!commentRepository.findAllByParentId(comment_id).isEmpty()){
            comment.setContents("게시자에 의하여 댓글이 삭제되었습니다");
            comment.setUser(null);
            comment.setRegdate(new Date());
        }
        else {
        commentRepository.delete(commentRepository.findByIdAndBoard(comment_id,board)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"해당 댓글을 찾을 수 없습니다",comment_id)));
        }
    }

    @Transactional
    public List<CommentResponseDTO> selectParentId(Long parentId){
        return CommentResponseDTO.addDtoList(commentRepository.findAllByParentId(parentId));
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
