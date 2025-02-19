package com.tobiaslescano.utils;

import com.tobiaslescano.models.DTOs.ErrorDTO;
import com.tobiaslescano.services.exceptions.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .errors(List.of(ex.getMessage()))
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleBadRequestException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        return new ResponseEntity<>(ErrorDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(List.of(ex.getMessage()))
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
