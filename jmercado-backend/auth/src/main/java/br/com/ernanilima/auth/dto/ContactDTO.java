package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import br.com.ernanilima.auth.utils.Validation;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ContactDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    private UUID id;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Email(regexp = Validation.EMAIL_REGEX, message = "{invalid.email}", groups = {Post.class, Put.class})
    private String email;

    @Length(min = 10, max = 15, message = "{length.field}", groups = {Post.class, Put.class})
    private String telephone;

    @Length(min = 10, max = 15, message = "{length.field}", groups = {Post.class, Put.class})
    private String cellPhone;

    private boolean whatsappCellPhone;

}
