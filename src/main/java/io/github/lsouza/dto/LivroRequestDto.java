package io.github.lsouza.dto;

import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LivroRequestDto(@NotBlank(message = "Campo obrigatório.")
                              String isbn,
                              @NotBlank(message = "Campo obrigatório.")
                              String titulo,
                              @NotNull(message = "Campo obrigatório.")
                              LocalDate dataPublicacao,
                              GeneroLivro genero,
                              Double preco,
                              @NotNull(message = "O livro deve conter o ID de um autor.")
                              UUID id_autor) {
}
