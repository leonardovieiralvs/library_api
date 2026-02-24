package io.github.lsouza.mapper;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.models.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public interface LivroMapper {


    Livro livroToEntity(LivroRequisicaoDto requisicaoDto);

//    @Mapping(source = "autor", target = "autorDto")
    LivroRespostaDto livroRespostaDto(Livro livro);

    void updateEntity(@MappingTarget Livro livro, LivroRequisicaoDto dto);

}
