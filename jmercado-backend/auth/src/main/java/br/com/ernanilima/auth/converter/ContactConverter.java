package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Contact;
import br.com.ernanilima.auth.dto.ContactDTO;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter implements DTOConverter<Contact, ContactDTO> {

    @Override
    public Contact toEntity(ContactDTO dto) {
        return Contact.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .cellPhone(dto.getCellPhone())
                .whatsappCellPhone(dto.isWhatsappCellPhone())
                .build();
    }
}
