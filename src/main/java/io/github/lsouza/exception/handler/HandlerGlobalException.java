package io.github.lsouza.exception.handler;

import io.github.lsouza.controller.dto.ErrorResponse;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerGlobalException {

    @ExceptionHandler(UnprocessableEntityException.class)
    private ResponseEntity<ErrorResponse> handlerUnprocessableEntityExcpetion (UnprocessableEntityException excpetion) {
        return new ResponseEntity<>(
                ErrorResponse.builder().
                        timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .mensagem("Erro de validação")
                        .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<ErrorResponse> handlerConflictException(ConflictException exception) {
        return new ResponseEntity<>(ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .mensagem("Autor já cadastrado")
                .build(), HttpStatus.CONFLICT);
    }
}
