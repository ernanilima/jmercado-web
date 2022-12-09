package br.com.ernanilima.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 8, max = 8, message = "{length.min.field}")
    private String zipCode;

    @NotEmpty(message = "{empty.field}")
    private String country;

    @NotEmpty(message = "{empty.field}")
    private String region;

    @NotEmpty(message = "{empty.field}")
    private String state;

    @NotEmpty(message = "{empty.field}")
    private String city;

    @NotEmpty(message = "{empty.field}")
    private String district;

    @NotEmpty(message = "{empty.field}")
    private String street;

    @NotEmpty(message = "{empty.field}")
    private String number;

    private String complement;

    @NotEmpty(message = "{empty.field}")
    @Length(max = 10, message = "{length.max.field}")
    private Integer code;

    @NotEmpty(message = "{empty.field}")
    @Length(min = 2, max = 2, message = "{length.min.field}")
    private Integer areaCode;

}
