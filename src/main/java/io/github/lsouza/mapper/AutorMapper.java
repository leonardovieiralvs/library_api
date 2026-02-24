package io.github.lsouza.mapper;

import io.github.lsouza.dto.AutorDto;
import io.github.lsouza.models.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDto toDto(Autor autor);

    Autor toEntity(AutorDto autorDTO);

}
