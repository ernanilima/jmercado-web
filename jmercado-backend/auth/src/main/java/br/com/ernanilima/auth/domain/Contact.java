package br.com.ernanilima.auth.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "contact")
public class Contact extends AuditingEntity implements AuthEntity<UUID>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_contact", length = 36, unique = true)
    private UUID id;

    @Type(type = "uuid-char")
    @JoinColumn(name = "id_join", nullable = false, updatable = false, insertable = false)
    private UUID idJoin;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 15, unique = true)
    private String telephone;

    @Column(name = "cell_phone", length = 15, unique = true)
    private String cellPhone;

    @Column(name = "whatsapp_cell_phone", nullable = false)
    private boolean whatsappCellPhone;

}
