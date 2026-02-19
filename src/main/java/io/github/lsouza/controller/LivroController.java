package io.github.lsouza.controller;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroRespostaDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(livroService.listarPorId(id));
    }


    @PostMapping
    public ResponseEntity<LivroRespostaDto> livroRequestDtoResponseEntity(@Valid @RequestBody LivroRequisicaoDto livroRequisicao) {

        LivroRespostaDto livroRespostaDto = livroService.salvarLivro(livroRequisicao);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroRespostaDto.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable UUID id) {
        livroService.deletarLivro(id);
        return ResponseEntity.ok().build();
    }


}
