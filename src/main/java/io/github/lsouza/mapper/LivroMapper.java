package io.github.lsouza.mapper;

import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.models.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public interface LivroMapper {


    Livro livroToEntity(io.github.lsouza.dto.LivroRequisicaoDto requisicaoDto);

//    @Mapping(source = "autor", target = "autorDto")
    LivroRespostaDto livroRespostaDto(Livro livro);

    void updateEntity(@MappingTarget Livro livro, io.github.lsouza.dto.LivroRequisicaoDto dto);

}
