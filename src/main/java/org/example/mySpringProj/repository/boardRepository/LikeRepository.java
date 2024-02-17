package org.example.mySpringProj.repository.boardRepository;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.boardDomain.Likes;
import org.example.mySpringProj.domain.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    @Query("select l from Likes l where l.board = :board and l.user = :user")
    Optional<Likes> findByBoardIdAndUserId(@Param("board") Board board, @Param("user") User user);
}
