package io.github.lsouza.validator;

import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.models.Autor;
import io.github.lsouza.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new ConflictException("Autor já cadastrado!");
        }
    }

    public boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(),
                autor.getDataNascimento(), autor.getNacionalidade());

        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
