package br.com.ernanilima.auth.builder;

import br.com.ernanilima.auth.domain.Contact;
import br.com.ernanilima.auth.dto.ContactDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContactBuilder {

    public static Contact create() {
        return Contact.builder()
                .id(UUID.fromString("730ef6a2-761a-44e9-9b92-a4ea4dc3366e"))
                .idJoin(UUID.randomUUID())
                .email("email1@email.com")
                .telephone("4144443333")
                .cellPhone("41944443333")
                .whatsappCellPhone(Boolean.TRUE)
                .build();
    }

    public static ContactDTO createDTO() {
        return ContactDTO.builder()
                .id(UUID.fromString("730ef6a2-761a-44e9-9b92-a4ea4dc3366e"))
                .email("email1@email.com")
                .telephone("4144443333")
                .cellPhone("41944443333")
                .whatsappCellPhone(Boolean.TRUE)
                .build();
    }

    public static ContactDTO createDTOToInsert() {
        return ContactDTO.builder()
                .email("email.insert@email.com")
                .telephone("4144444444")
                .cellPhone("41988888888")
                .build();
    }

    public static ContactDTO createDTOToUpdate() {
        return ContactDTO.builder()
                .id(UUID.fromString("9204d1ed-7e59-486b-878f-64a9af90595e"))
                .email("email.update@email.com")
                .telephone("4133333333")
                .cellPhone("41977777777")
                .build();
    }
}
