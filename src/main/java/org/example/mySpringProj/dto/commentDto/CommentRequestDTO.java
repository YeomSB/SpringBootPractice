package org.example.mySpringProj.dto.commentDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private Long id;
    private String contents;
}
