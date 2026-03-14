package io.github.lsouza.security;


import io.github.lsouza.models.Usuario;
import io.github.lsouza.service.UsuarioService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public CustomUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.obterPorLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

            return User.builder()
                    .username(usuario.getLogin())
                    .password(usuario.getSenha())
                    .roles(usuario.getRoles().toString())
                    .build();
    }
}
