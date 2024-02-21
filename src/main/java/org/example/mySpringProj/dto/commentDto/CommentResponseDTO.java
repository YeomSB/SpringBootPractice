package org.example.mySpringProj.dto.commentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.mySpringProj.domain.commentDomain.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class CommentResponseDTO {
    private String userNickName;
    @NotBlank(message = "대댓글 내용을 입력해주세요.")
    private String contents;
    private Date regDate;

    public static List<CommentResponseDTO> addDtoList(List<Comment> comments){
        List<CommentResponseDTO> commentResponseDTOList= new ArrayList<>();
        for(Comment comment : comments){
            commentResponseDTOList.add(
                    CommentResponseDTO.builder()
                            .userNickName(comment.getUser().getNickName())
                            .contents(comment.getContents())
                            .regDate(comment.getRegdate())
                            .build()
            );
        }
        return commentResponseDTOList;
    }
}
