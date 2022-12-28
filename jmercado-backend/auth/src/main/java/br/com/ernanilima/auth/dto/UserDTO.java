package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import static br.com.ernanilima.auth.service.validation.AuthRegex.EMAIL_REGEX;

@Builder(toBuilder = true)
@Getter
public class UserDTO implements DTOUpdate, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    private UUID id;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 8, max = 50, message = "{length.field}", groups = {Post.class, Put.class})
    private String name;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Email(regexp = EMAIL_REGEX, message = "{invalid.email}", groups = {Post.class, Put.class})
    private String email;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 6, max = 15, message = "{length.field}", groups = {Post.class, Put.class})
    private String password;

    @NotNull(message = "{empty.field}", groups = {Post.class})
    @JsonIgnoreProperties({"address", "contacts"})
    private CompanyDTO company;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
