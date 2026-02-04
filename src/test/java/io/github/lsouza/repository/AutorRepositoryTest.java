package io.github.lsouza.repository;

import io.github.lsouza.models.Autor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
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
        UUID id = UUID.fromString("3e97ccdc-ca95-41ee-a751-7e99cb164cf5");
        Optional<Autor> autorById = autorRepository.findById(id);

        if (autorById.isPresent()) {
            Autor autor = autorById.get();
            System.out.println("Dados do autor: " + autor);

            autor.setNome("Bira rei de thais");
            autorRepository.save(autor);
        } else {
            System.out.println("Autor não encontrado");
        }
    }

    @Test
    public void listarTodos() {
        List<Autor> todosAutores = autorRepository.findAll();
        todosAutores.forEach(System.out::println);
    }

    @Test
    public void contarAutores() {
        System.out.println("Contages de autores: " + autorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        var byId = UUID.fromString("d0a46e94-f370-4101-a655-95179c1cc7e9");
        autorRepository.deleteById(byId);
    }

    @Test
    public void delete() {
        UUID id = UUID.fromString("3e97ccdc-ca95-41ee-a751-7e99cb164cf5");

        var autor = autorRepository.findById(id).get();
        var byId = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("o"));
        autorRepository.delete(autor);
    }
}
