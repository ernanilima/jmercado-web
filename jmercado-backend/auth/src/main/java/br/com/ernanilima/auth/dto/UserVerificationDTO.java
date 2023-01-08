package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.dto.jkafka.SecurityVerificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
public class UserVerificationDTO extends SecurityVerificationDTO implements DTOUpdate, Serializable {

    private UUID id;
    private UserDTO user;
    private int minutesExpiration;
    private boolean valid;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
