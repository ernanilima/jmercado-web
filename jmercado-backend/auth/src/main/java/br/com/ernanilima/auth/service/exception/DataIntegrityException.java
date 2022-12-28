package br.com.ernanilima.auth.service.exception;

import java.io.Serial;

/**
 * Integridade de dados
 * Inserir ou atualizar
 */
public class DataIntegrityException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DataIntegrityException(String message) {
        super(message);
    }
}
