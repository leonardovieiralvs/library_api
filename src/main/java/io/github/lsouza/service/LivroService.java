package io.github.lsouza.service;

import io.github.lsouza.dto.LivroRequestDto;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.mapper.LivroMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;


    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, LivroMapper livroMapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroMapper = livroMapper;
    }

    public LivroRequestDto salvarLivro(LivroRequestDto livroRequestDto) {

        if (livroRepository.existsByIsbn(livroRequestDto.isbn())) {
            throw new ConflictException("ISBN já cadastrado no banco.");
        }

        Livro livroDto = livroMapper.livroToEntity(livroRequestDto);
        Autor autor = autorRepository.findById(livroRequestDto.id_autor()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        livroDto.setAutor(autor);

        Livro livroEntity = livroRepository.save(livroDto);

        return livroMapper.livroRequestToDto(livroEntity);
    }
}
