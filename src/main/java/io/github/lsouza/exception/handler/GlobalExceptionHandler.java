package io.github.lsouza.exception.handler;

import io.github.lsouza.dto.ErroCampoDto;
import io.github.lsouza.dto.ErrorRespostaDto;
import io.github.lsouza.exception.ConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespostaDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                                  HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ErroCampoDto> list = fieldErrors.stream().map(fe -> new ErroCampoDto(fe.getField(), fe.getDefaultMessage())).toList();
        ErrorRespostaDto erroDeValidacao = ErrorRespostaDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .mensagem("Erro de validação")
                .path(request.getRequestURI())
                .erros(list)
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroDeValidacao);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorRespostaDto> conflictHandlerException(ConflictException conflict,
                                                                     HttpServletRequest request) {
        ErrorRespostaDto autorCadastrado = ErrorRespostaDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .mensagem(conflict.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(autorCadastrado);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespostaDto> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                         HttpServletRequest request) {
        ErrorRespostaDto error = ErrorRespostaDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .mensagem("JSON inválido ou formato incorreto")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
    }
}
