package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.domain.Role;
import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class UserDTO extends UserBasicDTO implements DTOUpdate, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    private UUID id;

    @NotNull(message = "{empty.field}", groups = {Post.class})
    @JsonIgnoreProperties({"address", "contacts"})
    private CompanyDTO company;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    @JsonIgnoreProperties({"idDaddy", "description", "type", "roles"})
    private Set<Role> roles;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
