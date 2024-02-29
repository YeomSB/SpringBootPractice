package org.example.mySpringProj.exception;

import org.example.mySpringProj.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.dto.ValidErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDTO> handleAppException(AppException ex) {
        //String status = ex.getErrorCode().getMessage();
        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorStatus(ex.getErrorCode())
                .errorContent(ex.getMessage())
                .data(ex.getData())
                .build();

        return new ResponseEntity<>(errorDTO, ex.getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(RuntimeException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("type", "INTERNAL_SERVER_ERROR");
        errorResponse.put("message", e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidErrorDTO> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ValidErrorDTO validErrorDTO = ValidErrorDTO.builder()
                .errorCode("400")
                .errorContent(errorMessages)
                .build();

        return ResponseEntity.badRequest().body(validErrorDTO);
    }


}
