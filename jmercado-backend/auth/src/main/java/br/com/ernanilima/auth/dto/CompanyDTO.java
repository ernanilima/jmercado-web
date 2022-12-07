package br.com.ernanilima.auth.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class CompanyDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String companyName;
    private String tradingName;
    private String ein;
    private String email;

}