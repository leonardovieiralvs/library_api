package io.github.lsouza.mapper;

import io.github.lsouza.controller.dto.AutorDTO;
import io.github.lsouza.models.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDTO toDto(Autor autor);

    Autor toEntity(AutorDTO autorDTO);
}
