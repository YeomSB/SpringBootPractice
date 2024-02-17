package org.example.mySpringProj.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"400"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"402"),
    FORBIDDEN(HttpStatus.FORBIDDEN,"403"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"404"),
    CONFLICT(HttpStatus.CONFLICT,"409");

    private HttpStatus httpStatus;
    private String message;


}
