package org.example.mySpringProj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class ValidErrorDTO {
    private String errorCode;
    private List<String> errorContent;
}
