package io.github.lsouza.controller;

import io.github.lsouza.dto.AutorDTO;
import io.github.lsouza.dto.ErrorResponse;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {


    private final AutorService autorService;
    private final AutorMapper autorMapper;

    public AutorController(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(autorService.findById(id));
    }


    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                      @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<AutorDTO> result = autorService.search(nome, nacionalidade);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> save(@Valid @RequestBody AutorDTO autorDTO) {

            AutorDTO autorCreated = autorService.autorSave(autorDTO);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorCreated.id())
                    .toUri();
            return ResponseEntity.created(location).body(autorCreated);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> autorSave(@PathVariable UUID id, @RequestBody AutorDTO autor) {
        try {
            AutorDTO update = autorService.update(id, autor);
            return ResponseEntity.status(HttpStatus.OK).body(update);
        } catch (ConflictException e) {
            var erroDto = ErrorResponse.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        autorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
