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
}
