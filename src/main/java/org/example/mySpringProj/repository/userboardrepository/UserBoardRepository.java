package org.example.mySpringProj.repository.userboardrepository;

import org.example.mySpringProj.domain.boardDomain.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard,Long> {
    Optional<UserBoard> findByUserIdAndBoardId(Long userId, Long boardId);
}
