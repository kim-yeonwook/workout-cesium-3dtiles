package com.yw.cesium.infrastructure.handler;

import com.yw.cesium.infrastructure.exception.BadRequestException;
import com.yw.cesium.infrastructure.exception.InternalServerException;
import com.yw.cesium.web.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDTO<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder("Properties : ");
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            sb.append("'[").append(fieldError.getField()).append("] ").append(fieldError.getDefaultMessage()).append("', ");
        }
        String msg = sb.toString();
        log.error(msg, e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.builder()
                        .code(ResponseDTO.ERROR_CODE)
                        .msg(msg)
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ResponseDTO<?>> handleBadRequestException(BadRequestException e) {
        log.error("", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDTO.builder()
                        .code(ResponseDTO.ERROR_CODE)
                        .msg(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<ResponseDTO<?>> handleInternalServerException(InternalServerException e) {
        log.error("", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.builder()
                        .code(ResponseDTO.ERROR_CODE)
                        .msg(e.getMessage())
                        .build());
    }
}
