package io.github.lsouza.controller;

import io.github.lsouza.dto.LivroRequestDto;
import io.github.lsouza.dto.LivroResponseDto;
import io.github.lsouza.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> livroResponseDtoResponseEntity(@PathVariable LivroResponseDto livroResponseDto) {



    }



    @PostMapping
    public ResponseEntity<LivroRequestDto> livroRequestDtoResponseEntity(@Valid @RequestBody LivroRequestDto livroRequestDto) {

        LivroRequestDto livroRequestDtoSave = livroService.salvarLivro(livroRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroRequestDtoSave.id_autor())
                .toUri();

        return ResponseEntity.created(location).build();
    }




}
