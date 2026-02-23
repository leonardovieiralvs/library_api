package io.github.lsouza.service;


import io.github.lsouza.dto.AutorDto;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.exception.OperationNotAllowedException;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import io.github.lsouza.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public AutorDto autorSave(AutorDto autorDTO) {

        if (autorRepository.existsByNome(autorDTO.nome())) {
            throw new ConflictException("Autor já cadastrado");
        }

        Autor autor = autorMapper.toEntity(autorDTO);
        Autor autorSave = autorRepository.save(autor);

        return autorMapper.toDto(autorSave);
    }

    public AutorDto findById(UUID id) {
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

    public List<AutorDto> search(String nome, String nacionalidade) {

        List<Autor> autores;

        if (nome != null && nacionalidade != null) {
            autores = autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null) {
            autores = autorRepository.findByNome(nome);
        } else if (nacionalidade != null) {
            autores = autorRepository.findByNacionalidade(nacionalidade);
        } else {
            autores = autorRepository.findAll();
        }
        return autores.stream().map(autorMapper::toDto).toList();
    }

    public List<AutorDto> pesquisaByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNome(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        List<Autor> all = autorRepository.findAll(autorExample);
        return all.stream().map(autorMapper::toDto).toList();

    }

    public AutorDto update(UUID id, AutorDto autorAtualizado) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado!"));
//        autor.setId(autor.getId());
        autor.setNome(autorAtualizado.nome());
        autor.setDataNascimento(autorAtualizado.dataNascimento());
        autor.setNacionalidade(autorAtualizado.nacionalidade());
        return autorMapper.toDto(autor);
    }

    public boolean possuiLivro(Autor autor) {

        return livroRepository.existsByAutor(autor);
    }
}
