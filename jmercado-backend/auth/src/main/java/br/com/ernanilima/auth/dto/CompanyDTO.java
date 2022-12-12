package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class CompanyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    private UUID id;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 8, max = 50, message = "{length.field}", groups = {Post.class, Put.class})
    private String companyName;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 8, max = 50, message = "{length.field}", groups = {Post.class, Put.class})
    private String tradingName;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 14, max = 14, message = "{length.min.field}", groups = {Post.class, Put.class})
    @CNPJ(message = "{invalid.ein}", groups = {Post.class, Put.class})
    private String ein;

    @Valid
    @NotNull(message = "{empty.field}", groups = {Post.class, Put.class})
    private AddressDTO address;

    @Valid
    @NotNull(message = "{empty.field}", groups = {Post.class, Put.class})
    private Set<ContactDTO> contacts;

}
