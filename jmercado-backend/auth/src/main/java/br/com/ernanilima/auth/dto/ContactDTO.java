package br.com.ernanilima.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ContactDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String telephone;
    private String cellPhone;
    private boolean whatsappCellPhone;

}
