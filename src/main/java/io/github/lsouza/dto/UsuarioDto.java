package io.github.lsouza.dto;

import io.github.lsouza.enumeracao.UserRoles;

public record UsuarioDto(String login,
                         String senha,
                         UserRoles roles) {
}
