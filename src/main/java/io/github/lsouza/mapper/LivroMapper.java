package io.github.lsouza.mapper;

import io.github.lsouza.dto.LivroRequestDto;
import io.github.lsouza.models.Livro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroRequestDto livroRequestToDto(Livro livro);

    Livro livroToEntity(LivroRequestDto livroRequestDto);
}
