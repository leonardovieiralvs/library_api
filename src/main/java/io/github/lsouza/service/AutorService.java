package io.github.lsouza.service;


import io.github.lsouza.controller.dto.AutorDTO;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    public Autor autorSave(Autor autor) {
        return autorRepository.save(autor);
    }

    public Optional<Autor> findById(UUID id) {
        return autorRepository.findById(id);
    }

    public void deleteById(Autor autor) {
        autorRepository.delete(autor);
    }

    public List<Autor> search(String nome, String nacionalidade) {

        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();


    }

    @Transactional
    public AutorDTO update(UUID id, AutorDTO autorAtualizado) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado para atualização!"));
        autor.setId(autor.getId());
        autor.setNome(autorAtualizado.nome());
        autor.setDataNascimento(autorAtualizado.dataNascimento());
        autor.setNacionalidade(autorAtualizado.nacionalidade());
        return autorMapper.toDto(autor);
    }
}
