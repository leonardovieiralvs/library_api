package io.github.lsouza.service;

import io.github.lsouza.dto.UsuarioDto;
import io.github.lsouza.mapper.UsuarioMapper;
import io.github.lsouza.models.Usuario;
import io.github.lsouza.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDto salvarUsuario(UsuarioDto usuario) {
        Usuario user = usuarioMapper.toEntity(usuario);

        user.setSenha(passwordEncoder.encode(usuario.senha()));

        return usuarioMapper.toDto(usuarioRepository.save(user));
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
