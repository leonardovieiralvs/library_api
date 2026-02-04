package io.github.lsouza.repository;


import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;


    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("1234567-890123");
        livro.setPreco(199.90);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Stanley em Apuros 2");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 21));

        Autor autorExistente = autorRepository
                .findById(UUID.fromString("662308f1-d317-4c14-961f-0ccb47acd584"))
                .orElse(null);

        livro.setAutor(autorExistente);

        Livro save = livroRepository.save(livro);
        System.out.println("Livro create: " + save);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("1234567-890123");
        livro.setPreco(37.8);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Nação Dopamina");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 21));

        Autor autor = new Autor();
        autor.setNome("Anne lembek");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1982, 3, 23));

        livro.setAutor(autor);

        Livro save = livroRepository.save(livro);
        System.out.println("Livro create: " + save);
    }

    @Test
    void deleteAll() {
        livroRepository.deleteAll();
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID uuid = UUID.fromString("03a43b7e-fd16-49dd-b978-82003c46e473");
        Autor autorById = autorRepository.findById(uuid).get();

        autorById.setNome("David Goggins");
        Autor saveNovoNome = autorRepository.save(autorById);
        System.out.println("Nome Alterado: "+saveNovoNome);
    }
}