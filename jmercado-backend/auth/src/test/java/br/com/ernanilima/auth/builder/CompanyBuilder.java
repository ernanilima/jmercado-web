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

    public static CompanyDTO createDTOToInsert() {
        return CompanyDTO.builder()
                .companyName("Empersa Inserida LTDA")
                .tradingName("Empresa Inserida Muito Forte")
                .ein("00000000000949")
                .address(AddressBuilder.createDTOToInsert())
                .contacts(Set.of(ContactBuilder.createDTOToInsert()))
                .user(UserBuilder.createDTOToInsert())
                .build();
    }

    public static CompanyDTO createDTOToUpdate() {
        return CompanyDTO.builder()
                .id(UUID.fromString("db2c1162-998b-4204-afc5-2003a8eb8060"))
                .companyName("Empersa Atualizar LTDA")
                .tradingName("Empresa Atualizar Muito Forte")
                .ein("00000000000868")
                .address(AddressBuilder.createDTOToUpdate())
                .contacts(Set.of(ContactBuilder.createDTOToUpdate()))
                .build();
    }
}
