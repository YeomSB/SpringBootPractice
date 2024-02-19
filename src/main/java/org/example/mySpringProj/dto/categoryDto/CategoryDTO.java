package org.example.mySpringProj.dto.categoryDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryDTO {
    private String name;
    private List<String> tags;
    private Long userId;
}
