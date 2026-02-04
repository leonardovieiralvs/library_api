package io.github.lsouza.repository;

import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

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
        var byId = UUID.fromString("95f3a198-112a-4b07-b4c3-3a67b36cf804");
        autorRepository.deleteById(byId);
    }

    @Test
    public void deleteObj() {
        UUID id = UUID.fromString("5aa34d99-b1b6-471f-b239-9c1f84b3e05d");

        var autor = autorRepository.findById(id).get();
        autorRepository.delete(autor);
    }

    @Test
    void salvarAutorComLivros() {
        Autor autor = new Autor();
        autor.setNome("Thomas Anderssen");
        autor.setNacionalidade("Italiano");
        autor.setDataNascimento(LocalDate.of(1977, 1, 20));

        Livro livro = new Livro();
        livro.setIsbn("321221-890123");
        livro.setPreco(50.0);
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("MATRIX");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 21));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("321221-890123");
        livro2.setPreco(60.0);
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setTitulo("MATRIX 2");
        livro2.setDataPublicacao(LocalDate.of(2000, 1, 21));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }
}
