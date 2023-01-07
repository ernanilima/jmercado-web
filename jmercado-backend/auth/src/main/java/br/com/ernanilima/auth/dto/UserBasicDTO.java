package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

import static br.com.ernanilima.auth.service.validation.AuthRegex.EMAIL_REGEX;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class UserBasicDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 8, max = 50, message = "{length.field}", groups = {Post.class, Put.class})
    private String name;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Email(regexp = EMAIL_REGEX, message = "{invalid.email}", groups = {Post.class, Put.class})
    private String email;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 6, max = 15, message = "{length.field}", groups = {Post.class, Put.class})
    private String password;

}
