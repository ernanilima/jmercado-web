package br.com.ernanilima.auth.dto.auth;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

import static br.com.ernanilima.auth.service.validation.AuthRegex.EMAIL_REGEX;

@Builder
@Getter
public class LoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 14, max = 14, message = "{length.min.field}")
    private String ein;

    @NotEmpty(message = "{empty.field}")
    @Email(regexp = EMAIL_REGEX, message = "{invalid.email}")
    private String email;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 6, max = 15, message = "{length.field}")
    private String password;

}
