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
@Table(name = "userverification")
public class UserVerification extends AuditingEntity implements AuthEntity, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_verification", length = 36, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "security_link", nullable = false, unique = true)
    private String securityLink;

    @Column(name = "security_code", length = 6, nullable = false, unique = true)
    private String securityCode;

    @Column(name = "minutes_expiration", nullable = false)
    private int minutesExpiration;

    @Column(nullable = false)
    private boolean checked;

}
