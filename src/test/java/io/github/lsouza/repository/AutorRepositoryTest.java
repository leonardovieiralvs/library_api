package io.github.lsouza.repository;

import io.github.lsouza.models.Autor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Reinaldo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1982, 3, 23));

        var autorSave = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autorSave);
    }

    @Test
    public void atualizarTest() {
        UUID id = UUID.fromString("8ae93f24-fb2f-4fc3-8f11-56bb68956dd9");
        Optional<Autor> autorById = autorRepository.findById(id);

        if (autorById.isPresent()) {
            Autor autor = autorById.get();
            System.out.println("Dados do autor: " + autor);

            autor.setNome("Leonardo Bom de Briga");
            autor.setNacionalidade("Brasileiro");
            autor.setDataNascimento(LocalDate.of(1997, 3, 23));

            autorRepository.save(autor);
        } else {
            System.out.println("Autor não encontrado");
        }

    }
}
