package io.github.lsouza.service;


import io.github.lsouza.dto.AutorDTO;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.exception.OperationNotAllowedException;
import io.github.lsouza.exception.handler.GlobalExceptionHandler;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import io.github.lsouza.validator.AutorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper, AutorValidator autorValidator, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
    }

    public AutorDTO autorSave(AutorDTO autorDTO) {

        if (autorRepository.existsByNome(autorDTO.nome())) {
            throw new ConflictException("Autor já cadastrado");
        }

        Autor autor = autorMapper.toEntity(autorDTO);
        Autor autorSave = autorRepository.save(autor);

        return autorMapper.toDto(autorSave);
    }

    public AutorDTO findById(UUID id) {
        Autor autor = autorRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado!"));
        return autorMapper.toDto(autor);
    }

    public void deleteById(UUID id) {
        Autor autor = autorRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado!"));
        if (possuiLivro(autor)) {
            throw new OperationNotAllowedException("Não é possivel excluir um Autor que possui livros cadastrados");
        }

        autorRepository.delete(autor);
    }

    public List<AutorDTO> search(String nome, String nacionalidade) {

        if (nome != null && nacionalidade != null) {
            List<Autor> byNomeAndNacionalidade = autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
            return autorMapper.toListDto(byNomeAndNacionalidade);
        }

        if (nome != null) {
            List<Autor> byNome = autorRepository.findByNome(nome);
            return autorMapper.toListDto(byNome);
        }

        if (nacionalidade != null) {
            List<Autor> byNacionalidade = autorRepository.findByNacionalidade(nacionalidade);
            return autorMapper.toListDto(byNacionalidade);
        }

        List<Autor> all = autorRepository.findAll();
        return autorMapper.toListDto(all);

    }

    public AutorDTO update(UUID id, AutorDTO autorAtualizado) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado!"));
        autor.setId(autor.getId());
        autor.setNome(autorAtualizado.nome());
        autor.setDataNascimento(autorAtualizado.dataNascimento());
        autor.setNacionalidade(autorAtualizado.nacionalidade());
        return autorMapper.toDto(autor);
    }

    public boolean possuiLivro(Autor autor) {

        return livroRepository.existsByAutor(autor);
    }
}
