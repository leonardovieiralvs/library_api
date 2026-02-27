package io.github.lsouza.exception;

import lombok.Getter;

@Getter
public class CampoInvalidationException extends RuntimeException {

    private String campo;

    public CampoInvalidationException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
