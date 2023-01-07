package br.com.ernanilima.auth.dto.auth;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
public class TokenDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private String ein;
    private String email;

}
