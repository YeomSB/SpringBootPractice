package org.example.mySpringProj.dto.boardDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.mySpringProj.domain.boardDomain.Board;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class BoardResponseDTO {

    private String categoryName;
    private Long boardId;
    private String nickname;
    private String title;
    private String contents;
    private int likes;
    private int viewCnt;


    public static List<BoardResponseDTO> getDtoList(List<Board> boards) {
        List<BoardResponseDTO> boardResponseDTOS = new ArrayList<>();
        for(Board board : boards) {
            boardResponseDTOS.add(
                    BoardResponseDTO.builder()
                            .categoryName(board.getCategory().getName())
                            .boardId(board.getId())
                            .title(board.getTitle())
                            .nickname(board.getUser().getNickName())
                            .contents(board.getContents())
                            .likes(board.getLikeCount())
                            .viewCnt(board.getViewCount())
                            .build()
            );
        }
        return boardResponseDTOS;
    }
}
