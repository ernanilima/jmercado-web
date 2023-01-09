package br.com.ernanilima.auth.dto.jkafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String securityLink;
    @JsonIgnore
    private String securityCode;

}
