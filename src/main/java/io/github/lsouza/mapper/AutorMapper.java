package io.github.lsouza.mapper;

import io.github.lsouza.dto.AutorDTO;
import io.github.lsouza.models.Autor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDTO toDto(Autor autor);

    Autor toEntity(AutorDTO autorDTO);

    List<AutorDTO> toListDto(List<Autor> autor);
}
