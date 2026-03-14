package io.github.lsouza.controller;

import io.github.lsouza.dto.UsuarioDto;
import io.github.lsouza.models.Usuario;
import io.github.lsouza.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Usuario> obterPorLogin(@RequestParam (required = false) String login) {
        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);
        return ResponseEntity.ok().body(usuarioEncontrado);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> salvarUsuario(@RequestBody @Valid UsuarioDto usuario) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }
}
