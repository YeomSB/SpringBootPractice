package org.example.mySpringProj.repository.boardRepository;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.categoryDomain.Category;
import org.example.mySpringProj.domain.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser(User user);
    List<Board> findAllByCategory(Category category);
    void deleteAllByCategory(Category category);

    @Query("select b from Board b join fetch b.likes")
    Board findFetchLikesById(Long boardId);

    @Query("select b from Board b join fetch b.comments")
    Board findFetchCommentsById(Long boardId);
}
