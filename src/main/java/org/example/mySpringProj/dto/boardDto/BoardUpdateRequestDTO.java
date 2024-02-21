package org.example.mySpringProj.dto.boardDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDTO {
    @NotBlank(message = "글 내용을 입력해주세요.")
    private String contents;
}
