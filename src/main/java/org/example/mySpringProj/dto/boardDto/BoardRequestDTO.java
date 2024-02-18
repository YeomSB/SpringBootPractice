package org.example.mySpringProj.dto.boardDto;

import lombok.Getter;
import lombok.Setter;


@Getter
public class BoardRequestDTO {

    private long categoryId;
    private long userId;
    private String title;
    private String contents;

}
