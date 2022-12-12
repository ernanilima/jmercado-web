package br.com.ernanilima.auth.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company extends AuditingEntity implements AuthEntity<UUID>, Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_company", length = 36, unique = true)
    private UUID id;

    @Column(name = "company_name", length = 50, nullable = false)
    private String companyName;

    @Column(name = "trading_name", length = 50, nullable = false)
    private String tradingName;

    @Column(length = 20, unique = true, nullable = false)
    private String ein;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id_address")
    private Address address;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idJoin")
    private Set<Contact> contacts = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "company")
    private List<User> users = new ArrayList<>();

}
