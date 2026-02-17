package io.github.lsouza.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(LocalDateTime timestamp,
                            int status,
                            String mensagem,
                            String path,
                            List<ErroCampo> erros) {



    public static ErrorResponse conflito(String message) {
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), message, builder().path, List.of());
    }
}
