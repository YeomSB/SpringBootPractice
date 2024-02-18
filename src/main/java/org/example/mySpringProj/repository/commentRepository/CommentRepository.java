package org.example.mySpringProj.repository.commentRepository;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByBoard(Board board);
    List<Comment> getAllByUser(User user);
    Optional<Comment> findByIdAndBoard(Long commentId, Board board);
    void deleteAllByBoard(Board board);


}
