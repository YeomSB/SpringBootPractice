package org.example.mySpringProj.service.commentService;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.commentDomain.ReComment;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.dto.commentDto.CommentRequestDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.commentRepository.ReCommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReCommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;

    @Transactional
    public void save(CommentRequestDTO commentRequestDTO, String userName) {
        Comment comment = commentRepository.findById(commentRequestDTO.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 댓글이 없습니다.",commentRequestDTO.getId()));

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"",null));

        reCommentRepository.save(ReComment.builder()
                .comment(comment)
                .contents(commentRequestDTO.getContents())
                .user(user)
                .build());
    }

    @Transactional
    public void deleteReComment(Long commentId, Long recommentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 댓글이 없습니다.",commentId));

        reCommentRepository.delete(reCommentRepository.findReCommentByCommentAndId(comment,recommentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 대댓글이 없습니다.",recommentId))
        );
    }

    public List<CommentResponseDTO> getReComments(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 댓글이 없습니다.",commentId));

        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        List<ReComment> reComments = reCommentRepository.findALLByComment(comment);
        for(ReComment reComment : reComments){
            commentResponseDTOList.add(CommentResponseDTO.builder()
                    .userNickName(reComment.getUser().getNickName())
                    .contents(reComment.getContents())
                    .regDate(reComment.getRegdate())
                    .build());
        }
        return commentResponseDTOList;
    }
}
