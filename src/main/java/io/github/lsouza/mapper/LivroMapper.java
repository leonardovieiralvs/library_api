package io.github.lsouza.mapper;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.models.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivroMapper {


    Livro livroToEntity(LivroRequisicaoDto livroRequestDto);

    @Mapping(source = "autor", target = "autorRespostaDto")
    LivroRespostaDto livroRespostaDto(Livro livro);
}
