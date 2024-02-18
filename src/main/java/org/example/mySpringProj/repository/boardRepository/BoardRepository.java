package org.example.mySpringProj.repository.boardRepository;

import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.boardDomain.Category;
import org.example.mySpringProj.domain.userDomain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser(User user);
    List<Board> findAllByCategory(Category category);
    void deleteAllByCategory(Category category);
}
