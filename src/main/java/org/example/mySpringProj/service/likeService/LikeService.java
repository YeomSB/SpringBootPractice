package org.example.mySpringProj.service.likeService;

import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.boardDomain.Likes;
import org.example.mySpringProj.domain.userDomain.User;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.boardRepository.LikeRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Transactional
    public String like(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Optional<Likes> findLike = likeRepository.findByBoardIdAndUserId(board, user);

        if (findLike.isEmpty()) {
            Likes likes = Likes.builder()
                    .board(board)
                    .user(user)
                    .build();
            likeRepository.save(likes);
            return "좋아요";
        } else {
            likeRepository.delete(findLike.get());
            return "좋아요 취소";
        }


    }
}