package io.github.lsouza.dto;

import io.github.lsouza.enumeracao.GeneroLivro;

import java.time.LocalDate;
import java.util.UUID;

public record LivroRespostaDto(UUID id,
                               String isbn,
                               String titulo,
                               LocalDate dataPublicacao,
                               GeneroLivro genero,
                               Double preco,
                               AutorDto autor) {
}
