package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.utils.Validation;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String name;

    @NotEmpty(message = "{empty.field}")
    @Email(regexp = Validation.EMAIL_REGEX, message = "{invalid.email}")
    private String email;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 6, max = 15, message = "{length.field}")
    private String password;

}
