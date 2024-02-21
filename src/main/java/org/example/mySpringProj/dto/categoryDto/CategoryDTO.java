package org.example.mySpringProj.dto.categoryDto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryDTO {
    @NotBlank(message = "게시판 이름을 입력해주세요.")
    private String name;
    private List<String> tags;
    private Long userId;
}
