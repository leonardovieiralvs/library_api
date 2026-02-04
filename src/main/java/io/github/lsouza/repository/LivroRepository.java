package io.github.lsouza.repository;

import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

}
