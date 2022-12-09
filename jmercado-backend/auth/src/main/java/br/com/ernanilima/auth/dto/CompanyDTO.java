package br.com.ernanilima.auth.dto;

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

    private UUID id;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String companyName;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 50, message = "{length.field}")
    private String tradingName;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 14, max = 14, message = "{length.min.field}")
    @CNPJ(message = "{invalid.ein}")
    private String ein;

    @Valid
    @NotNull(message = "{empty.field}")
    private AddressDTO address;

    @Valid
    @NotNull(message = "{empty.field}")
    private Set<ContactDTO> contacts;

}
