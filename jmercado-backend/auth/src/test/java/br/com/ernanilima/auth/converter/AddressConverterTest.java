package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.builder.AddressBuilder;
import br.com.ernanilima.auth.domain.Address;
import br.com.ernanilima.auth.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddressConverterTest {

    @InjectMocks
    private AddressConverter addressConverterMock;

    @Test
    void toEntity() {
        AddressDTO dto = AddressBuilder.createDTO();
        Address entity = addressConverterMock.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getZipCode(), entity.getZipCode());
        assertEquals(dto.getCountry(), entity.getCountry());
        assertEquals(dto.getRegion(), entity.getRegion());
        assertEquals(dto.getState(), entity.getState());
        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getDistrict(), entity.getDistrict());
        assertEquals(dto.getStreet(), entity.getStreet());
        assertEquals(dto.getNumber(), entity.getNumber());
        assertEquals(dto.getComplement(), entity.getComplement());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getAreaCode(), entity.getAreaCode());
    }

    @Test
    void toDTO() {
        Address entity = AddressBuilder.create();
        AddressDTO dto = addressConverterMock.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getZipCode(), dto.getZipCode());
        assertEquals(entity.getCountry(), dto.getCountry());
        assertEquals(entity.getRegion(), dto.getRegion());
        assertEquals(entity.getState(), dto.getState());
        assertEquals(entity.getCity(), dto.getCity());
        assertEquals(entity.getDistrict(), dto.getDistrict());
        assertEquals(entity.getStreet(), dto.getStreet());
        assertEquals(entity.getNumber(), dto.getNumber());
        assertEquals(entity.getComplement(), dto.getComplement());
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getAreaCode(), dto.getAreaCode());
    }
}