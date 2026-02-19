package io.github.lsouza.dto;

import java.time.LocalDate;

public record AutorRespostaDto(String nome, LocalDate dataNascimento, String nacionalidade) {
}
