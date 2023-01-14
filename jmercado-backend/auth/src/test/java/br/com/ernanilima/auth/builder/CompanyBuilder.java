package br.com.ernanilima.auth.builder;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompanyBuilder {

    public static Company create() {
        return Company.builder()
                .id(UUID.fromString("7c0aa10d-9d62-4993-a086-2fcb8516aa52"))
                .companyName("Empersa LTDA")
                .tradingName("Empresa Muito Forte")
                .ein("00000000000191")
                .address(AddressBuilder.create())
                .contacts(Set.of(ContactBuilder.create()))
                .build();
    }

    public static CompanyDTO createDTO() {
        return CompanyDTO.builder()
                .id(UUID.fromString("7c0aa10d-9d62-4993-a086-2fcb8516aa52"))
                .companyName("Empersa LTDA")
                .tradingName("Empresa Muito Forte")
                .ein("00000000000191")
                .address(AddressBuilder.createDTO())
                .contacts(Set.of(ContactBuilder.createDTO()))
                .build();
    }
}
