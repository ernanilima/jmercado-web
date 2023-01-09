package br.com.ernanilima.auth.service.exception;

import java.io.Serial;

/**
 * Verificacao nao realizada
 */
public class VerificationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public VerificationException(String message) {
        super(message);
    }
}
