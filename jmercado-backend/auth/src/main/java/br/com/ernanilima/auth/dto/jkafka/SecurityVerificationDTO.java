package br.com.ernanilima.auth.dto.jkafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class SecurityVerificationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String securityLink;
    private String securityCode;

}
