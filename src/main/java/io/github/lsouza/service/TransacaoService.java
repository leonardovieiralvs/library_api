package io.github.lsouza.service;

import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar() {

        Autor autor = new Autor();
        autor.setNome("Morpheus");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1982, 3, 23));
        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("1234567-890123");
        livro.setPreco(37.8);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Matrix");
        livro.setDataPublicacao(LocalDate.of(1999, 1, 21));
        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Morpheus")) {
            throw new RuntimeException("Rollback");
        }
    }
}
