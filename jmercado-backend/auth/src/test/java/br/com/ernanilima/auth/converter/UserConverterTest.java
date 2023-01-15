package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.config.annotation.UnitTest;
import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserBasicDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@UnitTest
class UserConverterTest {

    @InjectMocks
    private UserConverter userConverterMock;

    @InjectMocks
    private CompanyConverter companyConverterMock;
    @InjectMocks
    private AddressConverter addressConverterMock;
    @InjectMocks
    private ContactConverter contactConverterMock;
    @Mock
    private ContactRepository contactRepositoryMock;

    @BeforeEach
    void setup() {
        contactConverterMock = new ContactConverter(contactRepositoryMock);
        companyConverterMock = new CompanyConverter(addressConverterMock, contactConverterMock);

        userConverterMock = new UserConverter(companyConverterMock);
    }

    @Test
    void toEntity() {
        UserDTO dto = UserBuilder.createDTO();
        User entity = userConverterMock.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getRoles(), entity.getRoles());
        assertEquals(dto.getRoles().stream().toList().get(0).getId(), entity.getRoles().stream().toList().get(0).getId());
        assertNull(entity.getRoles().stream().toList().get(0).getIdDaddy());
        assertEquals(dto.getRoles().stream().toList().get(0).getDescription(), entity.getRoles().stream().toList().get(0).getDescription());
        assertEquals(dto.getRoles().stream().toList().get(0).getType(), entity.getRoles().stream().toList().get(0).getType());
        assertEquals(entity.getRoles().stream().toList().get(0).getRoles().size(), 0);
        assertEquals(dto.getCompany().getId(), entity.getCompany().getId());
    }

    @Test
    void toDTO() {
        User entity = UserBuilder.create();
        UserDTO dto = userConverterMock.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertNull(dto.getPassword());
        assertEquals(entity.getRoles(), dto.getRoles());
        assertEquals(entity.getCompany().getId(), dto.getCompany().getId());
    }

    @Test
    void toDTOByUserBasicDTO() {
        UserBasicDTO basicDTO = UserBuilder.createBasicDTO();
        UserDTO dto = userConverterMock.toDTO(basicDTO);

        assertEquals(basicDTO.getName(), dto.getName());
        assertEquals(basicDTO.getEmail(), dto.getEmail());
        assertEquals(basicDTO.getPassword(), dto.getPassword());
    }
}