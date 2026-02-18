package io.github.lsouza.dto;

import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record LivroResponseDto(UUID id,
                               String isbn,
                               String titulo,
                               LocalDate dataPublicacao,
                               GeneroLivro generoLivro,
                               Double preco,
                               Autor autor) {
}
