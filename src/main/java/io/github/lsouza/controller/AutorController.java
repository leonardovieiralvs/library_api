package io.github.lsouza.controller;

import io.github.lsouza.controller.dto.AutorDTO;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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

        Optional<Autor> autorOptional = autorService.findById(id);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> result = autorService.search(nome, nacionalidade);
        List<AutorDTO> lista = result
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())).toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody AutorDTO autorDTO) {
        Autor autorEntity = autorDTO.mapperToDTO();
        autorService.autorSave(autorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntity.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> autorSave(@PathVariable UUID id, @RequestBody AutorDTO autor) {
        AutorDTO update = autorService.update(id, autor);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        Optional<Autor> autorOptional = autorService.findById(id);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        autorService.deleteById(autorOptional.get());
        return ResponseEntity.noContent().build();
    }
}
