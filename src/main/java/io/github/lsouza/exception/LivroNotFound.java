package io.github.lsouza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNotFound extends RuntimeException {
    public LivroNotFound(String message) {
        super(message);
    }
}
