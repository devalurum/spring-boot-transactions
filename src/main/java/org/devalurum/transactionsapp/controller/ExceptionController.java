package org.devalurum.transactionsapp.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.devalurum.transactionsapp.domain.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@Hidden
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ErrorDto error(RuntimeException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDto internalError(RuntimeException exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    public ErrorDto badRequest(Exception exception) {
        return new ErrorDto(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorDto conflict(IllegalStateException exception) {
        return new ErrorDto(exception.getMessage());
    }
}
