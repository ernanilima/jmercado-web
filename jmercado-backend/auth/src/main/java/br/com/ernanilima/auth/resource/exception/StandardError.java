package br.com.ernanilima.auth.resource.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Modelo padrao do erro para exibir
 */
@Getter
@SuperBuilder
public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
