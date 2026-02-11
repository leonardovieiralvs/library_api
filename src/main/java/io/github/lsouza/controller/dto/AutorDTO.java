package io.github.lsouza.controller.dto;

import io.github.lsouza.models.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank String nome,
                       @NotNull LocalDate dataNascimento,
                       @NotBlank String nacionalidade) {

}
