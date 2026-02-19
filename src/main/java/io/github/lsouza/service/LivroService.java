package io.github.lsouza.service;

import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.exception.LivroNotFoundException;
import io.github.lsouza.mapper.LivroMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;


    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, LivroMapper livroMapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroMapper = livroMapper;
    }


    public LivroRespostaDto listarPorId(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException("Livro não encontrado"));
        return livroMapper.livroRespostaDto(livro);
    }


    public LivroRespostaDto salvarLivro(LivroRequisicaoDto livroRequisicaoDto) {

        if (livroRepository.existsByIsbn(livroRequisicaoDto.isbn())) {
            throw new ConflictException("ISBN já cadastrado no banco.");
        }

        Livro livro = livroMapper.livroToEntity(livroRequisicaoDto);

        Autor autor = autorRepository.findById(livroRequisicaoDto.id_autor()).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        livro.setAutor(autor);

        Livro save = livroRepository.save(livro);

        return livroMapper.livroRespostaDto(save);
    }

    public LivroRespostaDto atualizarLivro(UUID id, LivroRequisicaoDto livroRequisicaoDto) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException("Livro inexistente"));

        livro.setIsbn(livroRequisicaoDto.isbn());
        livro.setTitulo(livroRequisicaoDto.titulo());
        livro.setDataPublicacao(livroRequisicaoDto.dataPublicacao());
        livro.setGenero(livroRequisicaoDto.genero());
        livro.setPreco(livroRequisicaoDto.preco());
        livro.setId(livroRequisicaoDto.id_autor());

        return livroMapper.livroRespostaDto(livro);
    }


    public void deletarLivro(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException("Livro inexistente"));
        livroRepository.delete(livro);
    }
}
