package br.com.ernanilima.auth.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company implements JMercadoEntity<UUID>, Serializable {

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

    @Column(length = 50, unique = true, nullable = false)
    private String email;

}
