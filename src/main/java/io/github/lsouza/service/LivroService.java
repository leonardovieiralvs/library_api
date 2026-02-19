package io.github.lsouza.service;

import io.github.lsouza.dto.AutorDto;
import io.github.lsouza.dto.LivroRequisicaoDto;
import io.github.lsouza.dto.LivroRespostaDto;
import io.github.lsouza.exception.ConflictException;
import io.github.lsouza.mapper.AutorMapper;
import io.github.lsouza.mapper.LivroMapper;
import io.github.lsouza.models.Autor;
import io.github.lsouza.models.Livro;
import io.github.lsouza.repository.AutorRepository;
import io.github.lsouza.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AutorService autorService;
    private final LivroMapper livroMapper;
    private final AutorMapper autorMapper;


    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, AutorService service, LivroMapper livroMapper, AutorMapper autorMapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.autorService = service;
        this.livroMapper = livroMapper;
        this.autorMapper = autorMapper;
    }

    public LivroRespostaDto salvarLivro(LivroRequisicaoDto livroRequisicaoDto) {


        if (livroRepository.existsByIsbn(livroRequisicaoDto.isbn())) {
            throw new ConflictException("ISBN já cadastrado no banco.");
        }

        Livro livro = livroMapper.livroToEntity(livroRequisicaoDto);

        // BUSCA ENTITY, não DTO
        Autor autor = autorRepository.findById(livroRequisicaoDto.id_autor()).orElseThrow(() -> new RuntimeException ("Autor não encontrado"));
        livro.setAutor(autor);

        Livro save = livroRepository.save(livro);

        return livroMapper.livroRespostaDto(save);
    }

    public LivroRespostaDto listarPorId(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return livroMapper.livroRespostaDto(livro);
    }
}
