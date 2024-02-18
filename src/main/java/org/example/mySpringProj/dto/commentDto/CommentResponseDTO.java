package org.example.mySpringProj.dto.commentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CommentResponseDTO {
    private String userNickName;
    private String contents;
    private Date regDate;
}
