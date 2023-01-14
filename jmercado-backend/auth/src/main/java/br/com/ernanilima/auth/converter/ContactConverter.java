package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Contact;
import br.com.ernanilima.auth.dto.ContactDTO;
import br.com.ernanilima.auth.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ContactConverter implements DTOConverter<Contact, ContactDTO> {

    private final ContactRepository contactRepository;

    @Override
    public Contact toEntity(ContactDTO dto) {
        return Contact.builder()
                .id(dto.getId())
                .idJoin(getIdJoin(dto))
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .cellPhone(dto.getCellPhone())
                .whatsappCellPhone(dto.isWhatsappCellPhone())
                .build();
    }

    @Override
    public ContactDTO toDTO(Contact entity) {
        return ContactDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .cellPhone(entity.getCellPhone())
                .whatsappCellPhone(entity.isWhatsappCellPhone())
                .build();
    }

    protected UUID getIdJoin(ContactDTO dto) {
        if (Objects.isNull(dto.getId()))
            return null;

        Optional<Contact> contact = contactRepository.findById(dto.getId());

        return contact.map(Contact::getIdJoin)
                .orElse(null);
    }
}
