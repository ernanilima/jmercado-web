package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserVerificationConverterTest {

    @InjectMocks
    private UserVerificationConverter userVerificationConverterMock;

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

        userVerificationConverterMock = new UserVerificationConverter(userConverterMock);
    }

    @Test
    void toEntity() {
        UserVerificationDTO dto = UserBuilder.createVerificationDTO();
        UserVerification entity = userVerificationConverterMock.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getUser().getId(), entity.getUser().getId());
        assertEquals(dto.getSecurityLink(), entity.getSecurityLink());
        assertEquals(dto.getSecurityCode(), entity.getSecurityCode());
        assertEquals(dto.getMinutesExpiration(), entity.getMinutesExpiration());
        assertEquals(dto.isChecked(), entity.isChecked());
    }

    @Test
    void toDTO() {
        UserVerification entity = UserBuilder.createVerification();
        UserVerificationDTO dto = userVerificationConverterMock.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUser().getId(), dto.getUser().getId());
        assertEquals(entity.getSecurityLink(), dto.getSecurityLink());
        assertEquals(entity.getSecurityCode(), dto.getSecurityCode());
        assertEquals(entity.getMinutesExpiration(), dto.getMinutesExpiration());
        assertEquals(entity.isChecked(), dto.isChecked());
    }
}