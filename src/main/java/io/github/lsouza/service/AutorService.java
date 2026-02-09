package io.github.lsouza.service;


import io.github.lsouza.models.Autor;
import io.github.lsouza.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor autorSave(Autor autor) {
        return autorRepository.save(autor);
    }
}
