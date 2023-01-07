package br.com.ernanilima.auth.service.exception;

import java.io.Serial;

/**
 * Autenticacao nao realizada
 */
public class JwtAuthenticationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
