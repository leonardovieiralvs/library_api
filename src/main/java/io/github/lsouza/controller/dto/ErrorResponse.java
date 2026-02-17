package io.github.lsouza.controller.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(LocalDateTime timestamp,
                            int status,
                            String mensagem,
                            List<ErroCampo> erros) {



    public static ErrorResponse respostaPadrao(String message) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponse conflito(String message) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), message, List.of());
    }
}
