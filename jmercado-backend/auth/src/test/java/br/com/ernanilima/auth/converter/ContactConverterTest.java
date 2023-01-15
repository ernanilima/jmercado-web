package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.builder.ContactBuilder;
import br.com.ernanilima.auth.config.annotation.UnitTest;
import br.com.ernanilima.auth.domain.Contact;
import br.com.ernanilima.auth.dto.ContactDTO;
import br.com.ernanilima.auth.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@UnitTest
class ContactConverterTest {

    @InjectMocks
    private ContactConverter contactConverterMock;

    @Mock
    private ContactRepository contactRepositoryMock;

    @BeforeEach
    void setup() {
        contactConverterMock = new ContactConverter(contactRepositoryMock);
    }

    @Test
    void toEntity() {
        ContactDTO dto = ContactBuilder.createDTO();

        when(contactRepositoryMock.findById(any())).thenReturn(Optional.empty());

        Contact entity = contactConverterMock.toEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertNull(entity.getIdJoin());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getTelephone(), entity.getTelephone());
        assertEquals(dto.getCellPhone(), entity.getCellPhone());
        assertEquals(dto.isWhatsappCellPhone(), entity.isWhatsappCellPhone());
    }

    @Test
    void toDTO() {
        Contact entity = ContactBuilder.create();
        ContactDTO dto = contactConverterMock.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getTelephone(), dto.getTelephone());
        assertEquals(entity.getCellPhone(), dto.getCellPhone());
        assertEquals(entity.isWhatsappCellPhone(), dto.isWhatsappCellPhone());
    }

    @Test
    @DisplayName("Deve retornar o uuid nulo para o objeto sem id")
    void getIdJoin_Must_Return_Null_Uuid_For_Object_Without_Id() {
        UUID uuid = contactConverterMock.getIdJoin(ContactDTO.builder().build());

        assertNull(uuid);
    }

    @Test
    @DisplayName("Deve retornar o uuid nulo para o objeto com id sem referencia")
    void getIdJoin_Must_Return_Null_Uuid_For_Object_With_Unreferenced_Id() {
        when(contactRepositoryMock.findById(any())).thenReturn(Optional.empty());

        UUID uuid = contactConverterMock.getIdJoin(ContactBuilder.createDTO());

        assertNull(uuid);
    }

    @Test
    @DisplayName("Deve retornar o uuid para o objeto com id com referencia")
    void getIdJoin_Must_Return_The_Uuid_For_The_Object_With_Id_With_Reference() {
        when(contactRepositoryMock.findById(any())).thenReturn(Optional.of(ContactBuilder.create()));

        UUID uuid = contactConverterMock.getIdJoin(ContactBuilder.createDTO());

        assertNotNull(uuid);
    }
}