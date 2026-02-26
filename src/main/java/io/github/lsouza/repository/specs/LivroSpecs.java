package io.github.lsouza.repository.specs;

import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.models.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEquals(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> cb.like(cb.upper(root.join("autor").get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEquals(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEquals(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("anoPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }
}
