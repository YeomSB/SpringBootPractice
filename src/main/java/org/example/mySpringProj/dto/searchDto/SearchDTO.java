package org.example.mySpringProj.dto.searchDto;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchDTO {

    @Nullable
    private Long userId;
    @Nullable
    private Long categoryId;
    @Nullable
    private Long boardId;
    @Nullable
    private String title;
    @Nullable
    private String content;


}
