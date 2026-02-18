package io.github.lsouza.repository;

import io.github.lsouza.dto.LivroRequestDto;
import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);
    boolean existsByAutor(Autor autor);

    // JPQL -> referência as entidade e as prioridades(atributos);
    @Query("select l from Livro as l order by l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate localDate);

    boolean existsByIsbn(String isbn);

}
