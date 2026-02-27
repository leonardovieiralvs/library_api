package io.github.lsouza.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorRespostaDto(LocalDateTime timestamp,
                               int status,
                               String mensagem,
                               String path,
                               List<ErroCampoDto> erros) {
}
