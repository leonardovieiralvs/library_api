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

    public Autor mapperToDTO() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
