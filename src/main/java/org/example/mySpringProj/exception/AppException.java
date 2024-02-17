package org.example.mySpringProj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
    protected ErrorCode errorCode;
    private String message;
    private Object data;
}
