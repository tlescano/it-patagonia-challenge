package com.tobiaslescano.utils;

import com.tobiaslescano.models.DTOs.ErrorDTO;
import com.tobiaslescano.services.exceptions.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
