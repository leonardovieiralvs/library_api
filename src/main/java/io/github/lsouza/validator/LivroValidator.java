package io.github.lsouza.validator;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.exception.CampoInvalidationException;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.stereotype.Component;

@Component
public class LivroValidator {

    private final static int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public LivroValidator(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void validar(LivroRequisicaoDto livro) {

        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidationException("preco", "Para livros com a data acima de 2020, campo obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNulo(LivroRequisicaoDto livro) {
        return livro.preco() == null && livro.dataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }
}
