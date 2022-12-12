package br.com.ernanilima.auth.dto;

import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "{empty.field}", groups = {Put.class})
    private UUID id;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    @Length(min = 8, max = 8, message = "{length.min.field}", groups = {Post.class, Put.class})
    private String zipCode;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String country;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String region;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String state;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String city;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String district;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String street;

    @NotEmpty(message = "{empty.field}", groups = {Post.class, Put.class})
    private String number;

    private String complement;

    @NotNull(message = "{empty.field}", groups = {Post.class, Put.class})
    private Integer code;

    @NotNull(message = "{empty.field}", groups = {Post.class, Put.class})
    private Integer areaCode;

}
