package io.github.lsouza.service;

import io.github.lsouza.dto.AutorDto;
import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.enumeracao.GeneroLivro;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.exception.LivroNotFoundException;
import io.github.lsouza.mapper.LivroMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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

        Autor autor = autorRepository.findById(livroRequisicaoDto.idAutor()).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        livro.setAutor(autor);

        Livro save = livroRepository.save(livro);

        return livroMapper.livroRespostaDto(save);
    }

    public LivroRespostaDto atualizarLivro(UUID id, LivroRequisicaoDto livroRequisicaoDto) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException("Livro inexistente"));
        if (livroRepository
                .existsByIsbnOrTitulo(livroRequisicaoDto.isbn(), livroRequisicaoDto.titulo())) {
            throw new ConflictException("Isbn ou Titulo ja existente no banco.");
        }

        livroMapper.updateEntity(livro, livroRequisicaoDto);
        livroRepository.save(livro);

        return livroMapper.livroRespostaDto(livro);

    }


    public void deletarLivro(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new LivroNotFoundException("Livro inexistente"));
        livroRepository.delete(livro);
    }

//    public List<LivroRespostaDto> pesquisaByExample(UUID id, String isbn, String titulo, String nome, GeneroLivro genero, LocalDate dataPublicacao) {
//        Livro livro = new Livro();
//        livro.setIsbn(isbn);
//        livro.setTitulo(titulo);
//        livro.getAutor().setNome(nome);
//        livro.setGenero(genero);
//        livro.setDataPublicacao(dataPublicacao);
//
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreNullValues()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        Example<Livro> livroExample = Example.of(livro, matcher);
//
//        List<Livro> all = livroRepository.findAll(livroExample);
//
//        return all.stream().map(livroMapper::livroRespostaDto).toList();
//    }
}
