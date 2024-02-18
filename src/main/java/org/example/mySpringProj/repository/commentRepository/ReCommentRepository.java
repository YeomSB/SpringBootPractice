package org.example.mySpringProj.repository.commentRepository;

import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.commentDomain.ReComment;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    Optional<ReComment> findReCommentByCommentAndId(Comment comment, Long recommentID);

    List<ReComment> findALLByComment(Comment comment);
}
