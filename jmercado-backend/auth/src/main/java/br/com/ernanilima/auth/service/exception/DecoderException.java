package br.com.ernanilima.auth.service.exception;

import java.io.Serial;

/**
 * Decodificador
 */
public class DecoderException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DecoderException(String message) {
        super(message);
    }
}
