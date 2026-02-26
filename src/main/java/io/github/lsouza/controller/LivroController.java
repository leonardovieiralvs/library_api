package io.github.lsouza.controller;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.mapper.GenericController;
import io.github.lsouza.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroRespostaDto> procurarLivroPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(livroService.listarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<LivroRespostaDto>> pesquisa(@RequestParam(value = "isbn", required = false) String isbn,
                                                           @RequestParam(value = "nome-autor", required = false) String nome,
                                                           @RequestParam(value = "genero", required = false) GeneroLivro genero,
                                                           @RequestParam(value = "ano-publicacao", required = false) Integer dataPublicacao) {

        List<LivroRespostaDto> pesquisa = livroService.pesquisa(isbn, nome, genero, dataPublicacao);
        return ResponseEntity.status(HttpStatus.OK).body(pesquisa);
    }


    @PostMapping
    public ResponseEntity<LivroRespostaDto> salvarLivro(@Valid @RequestBody LivroRequisicaoDto livroRequisicao) {

        LivroRespostaDto livroRespostaDto = livroService.salvarLivro(livroRequisicao);

        URI location = gerarHeaderLocation(livroRespostaDto.id());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarLivro(@PathVariable UUID id, @Valid @RequestBody LivroRequisicaoDto livroRequisicaoDto) {
        livroService.atualizarLivro(id, livroRequisicaoDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable UUID id) {
        livroService.deletarLivro(id);
        return ResponseEntity.ok().build();
    }


}
