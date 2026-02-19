package io.github.lsouza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNotFoundException extends RuntimeException {

    public LivroNotFoundException(String message) {
        super("Livro inexistente");
    }

//    public LivroNotFound(String message) {
//        super(message);
//    }
}
