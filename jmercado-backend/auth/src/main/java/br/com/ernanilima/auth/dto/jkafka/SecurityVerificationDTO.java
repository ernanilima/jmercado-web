package br.com.ernanilima.auth.dto.jkafka;

import br.com.ernanilima.auth.service.validation.Put;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class SecurityVerificationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{empty.field}", groups = {Put.class})
    private String securityLink;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "{empty.field}", groups = {Put.class})
    private String securityCode;

}
