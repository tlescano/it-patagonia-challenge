package com.tobiaslescano.config;

import com.tobiaslescano.models.DTOs.ErrorDTO;
import com.tobiaslescano.services.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerConfig {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .localizedMessage(ex.getLocalizedMessage())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .localizedMessage(ex.getLocalizedMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }
}
