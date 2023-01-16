package br.com.ernanilima.auth.builder;

import br.com.ernanilima.auth.domain.Address;
import br.com.ernanilima.auth.dto.AddressDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AddressBuilder {

    public static Address create() {
        return Address.builder()
                .id(UUID.fromString("f266ae81-dc50-4ff4-8b30-fbb3626d0423"))
                .zipCode("01001000")
                .country("Brasil")
                .region("Sul")
                .state("Parana")
                .city("Curitiba")
                .district("Centro")
                .street("Rua Principal")
                .number("S/N")
                .complement("Predio rebaixado")
                .code(4106902)
                .areaCode(41)
                .build();
    }

    public static AddressDTO createDTO() {
        return AddressDTO.builder()
                .id(UUID.fromString("f266ae81-dc50-4ff4-8b30-fbb3626d0423"))
                .zipCode("01001000")
                .country("Brasil")
                .region("Sul")
                .state("Parana")
                .city("Curitiba")
                .district("Centro")
                .street("Rua Principal")
                .number("S/N")
                .complement("Predio rebaixado")
                .code(4106902)
                .areaCode(41)
                .build();
    }

    public static AddressDTO createDTOToInsert() {
        return AddressDTO.builder()
                .zipCode("01001000")
                .country("Brasil")
                .region("Sul")
                .state("Parana")
                .city("Curitiba")
                .district("Centro")
                .street("Rua Principal Inserida")
                .number("S/N")
                .complement("Predio rebaixado")
                .code(4106902)
                .areaCode(41)
                .build();
    }

    public static AddressDTO createDTOToUpdate() {
        return AddressDTO.builder()
                .id(UUID.fromString("7dc8b537-3bcb-42fa-b9af-eb5a5cb9300c"))
                .zipCode("01001000")
                .country("Brasil - Alterado")
                .region("Sul - Alterado")
                .state("Parana - Alterado")
                .city("Curitiba - Alterado")
                .district("Centro - Alterado")
                .street("Rua Principal - Alterado")
                .number("Alterado")
                .complement("Predio rebaixado - Alterado")
                .code(7654321)
                .areaCode(12)
                .build();
    }
}
