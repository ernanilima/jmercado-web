package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Address;
import br.com.ernanilima.auth.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter implements DTOConverter<Address, AddressDTO> {

    @Override
    public Address toEntity(AddressDTO dto) {
        return Address.builder()
                .id(dto.getId())
                .zipCode(dto.getZipCode())
                .country(dto.getCountry())
                .region(dto.getRegion())
                .state(dto.getState())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .code(dto.getCode())
                .areaCode(dto.getAreaCode())
                .build();
    }

    @Override
    public AddressDTO toDTO(Address entity) {
        return AddressDTO.builder()
                .id(entity.getId())
                .zipCode(entity.getZipCode())
                .country(entity.getCountry())
                .region(entity.getRegion())
                .state(entity.getState())
                .city(entity.getCity())
                .district(entity.getDistrict())
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .code(entity.getCode())
                .areaCode(entity.getAreaCode())
                .build();
    }
}
