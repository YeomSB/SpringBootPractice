package org.example.mySpringProj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
    protected HttpStatus errorCode;
    private String message;
    private Object data;
}
