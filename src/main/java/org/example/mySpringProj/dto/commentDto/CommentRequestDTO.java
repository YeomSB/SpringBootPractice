package org.example.mySpringProj.dto.commentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long boardId;
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String contents;
    private Long parentId;
}
