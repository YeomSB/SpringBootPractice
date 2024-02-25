package org.example.mySpringProj.repository.searchRepository;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Board, Long> {


    @Query(
            "SELECT DISTINCT b FROM Board b WHERE (:userId IS NULL OR b.user.id = :userId) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "AND (:title IS NULL OR b.title LIKE CONCAT('%', :title, '%')) " +
            "AND (:content IS NULL OR b.contents LIKE CONCAT('%', :content, '%'))"
    )
    List<Board> findBoards(@Param("userId") Long userId,
                           @Param("categoryId") Long categoryId,
                           @Param("title") String title,
                           @Param("content") String content);

    @Query("SELECT c FROM Comment c WHERE (:userId IS NULL OR c.user.id = :userId) AND (:boardId IS NULL OR c.board.id = :boardId) AND (:content IS NULL OR c.contents = :content)")
    List<Comment> findComments(Long userId,Long boardId,String content);


}
