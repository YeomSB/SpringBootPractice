package org.example.mySpringProj.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter @Builder
public class ErrorDTO {
    private HttpStatus errorStatus;
    private String errorContent;
    private Object data;
}
