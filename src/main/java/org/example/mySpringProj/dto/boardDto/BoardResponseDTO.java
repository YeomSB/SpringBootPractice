package org.example.mySpringProj.dto.boardDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoardResponseDTO {

    private String userNickname;
    private String title;
    private String contents;
}
