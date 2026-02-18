package io.github.lsouza.mapper;

import io.github.lsouza.dto.AutorDto;
import io.github.lsouza.models.Autor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDto toDto(Autor autor);

    Autor toEntity(AutorDto autorDTO);

    List<AutorDto> toListDto(List<Autor> autor);
}
