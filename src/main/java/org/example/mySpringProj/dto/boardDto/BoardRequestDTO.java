package org.example.mySpringProj.dto.boardDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
public class BoardRequestDTO {

    @NotBlank(message = "게시판ID를 입력해주세요")
    private long categoryId;
    @NotBlank(message = "글 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "글 내용을 입력해주세요.")
    private String contents;

}
