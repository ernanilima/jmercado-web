package br.com.ernanilima.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class AddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String zipCode;

    private String country;

    private String region;

    private String state;

    private String city;

    private String district;

    private String street;

    private String number;

    private String complement;

    private Integer code;

    private Integer areaCode;

}
