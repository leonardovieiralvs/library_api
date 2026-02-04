package io.github.lsouza.repository;


import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void listarTodos() {
        List<Livro> listAll = livroRepository.findAll();
        listAll.forEach(System.out::println);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID uuid = UUID.fromString("777681e6-139e-458f-9879-6567ce2e0777");
        Livro livro = livroRepository.findById(uuid).orElse(null);
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());

    }

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("1234567-890123");
        livro.setPreco(199.90);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("LVS THE WHITE DRAGON");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 21));

        Autor autorExistente = autorRepository
                .findById(UUID.fromString("8ae93f24-fb2f-4fc3-8f11-56bb68956dd9"))
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
    public void deleteByIdTest() {
        var byId = UUID.fromString("60c16a5b-0fcb-4d8a-a743-48e1c57d7ffd");
        livroRepository.deleteById(byId);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID uuid = UUID.fromString("03a43b7e-fd16-49dd-b978-82003c46e473");
        Autor autorById = autorRepository.findById(uuid).get();

        autorById.setNome("David Goggins");
        Autor saveNovoNome = autorRepository.save(autorById);
        System.out.println("Nome Alterado: " + saveNovoNome);
    }

    @Test
    void listarLivrosPorAutor() {
        UUID uuid = UUID.fromString("14dff49f-9def-490d-9276-d8d71e921cab");
        Autor autor = autorRepository.findById(uuid).get();

        List<Livro> livrosByAutor = livroRepository.findByAutor(autor);
        livrosByAutor.forEach(System.out::println);
    }
}