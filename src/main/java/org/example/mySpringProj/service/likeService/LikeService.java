package org.example.mySpringProj.service.likeService;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.boardDomain.UserBoard;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.repository.userboardrepository.UserBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;

    @Transactional
    public String like(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();


        if (userBoardRepository.findByUserIdAndBoardId(userId, boardId).isEmpty()) {
            UserBoard userBoard = UserBoard.builder()
                    .board(board)
                    .user(user)
                    .build();
            userBoardRepository.save(userBoard);
            board.updateLikeCount();
            return "좋아요";
        }

        UserBoard findUserBoard = userBoardRepository.findByUserIdAndBoardId(userId, boardId).get();
            userBoardRepository.delete(findUserBoard);
            board.downLikeCount();
            return "좋아요 취소";



    }
}