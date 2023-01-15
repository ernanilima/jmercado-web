package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.builder.CompanyBuilder;
import br.com.ernanilima.auth.config.annotation.UnitTest;
import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.domain.Contact;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.ContactDTO;
import br.com.ernanilima.auth.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
class CompanyConverterTest {

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
    }

    @Test
    void toEntity() {
        CompanyDTO dto = CompanyBuilder.createDTO();
        Company entity = companyConverterMock.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCompanyName(), entity.getCompanyName());
        assertEquals(dto.getTradingName(), entity.getTradingName());
        assertEquals(dto.getEin(), entity.getEin());
        assertEquals(dto.getAddress().getId(), entity.getAddress().getId());
        assertEquals(dto.getContacts().stream().map(ContactDTO::getId).findFirst(), entity.getContacts().stream().map(Contact::getId).findFirst());
    }

    @Test
    void toDTO() {
        Company entity = CompanyBuilder.create();
        CompanyDTO dto = companyConverterMock.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCompanyName(), dto.getCompanyName());
        assertEquals(entity.getTradingName(), dto.getTradingName());
        assertEquals(entity.getEin(), dto.getEin());
        assertEquals(entity.getAddress().getId(), dto.getAddress().getId());
        assertEquals(entity.getContacts().stream().map(Contact::getId).findFirst(), dto.getContacts().stream().map(ContactDTO::getId).findFirst());
    }
}