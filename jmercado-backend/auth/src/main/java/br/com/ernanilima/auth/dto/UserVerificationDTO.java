package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.dto.jkafka.SecurityVerificationDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class UserVerificationDTO extends SecurityVerificationDTO implements DTOUpdate, Serializable {

    private UUID id;
    @JsonIgnoreProperties({"company", "roles"})
    private UserDTO user;
    private int minutesExpiration;
    private boolean checked;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
