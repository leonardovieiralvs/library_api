package io.github.lsouza.mapper;

import io.github.lsouza.dto.UsuarioDto;
import io.github.lsouza.models.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toDto(Usuario usuario);

    Usuario toEntity(UsuarioDto usuarioDto);
}
