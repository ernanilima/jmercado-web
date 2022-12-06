package br.com.ernanilima.auth.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "id_company", length = 36, unique = true)
    private UUID idCompany;

    @Column(name = "company_name", length = 50, nullable = false)
    private String companyName; // razao social

    @Column(name = "trading_name", length = 50, nullable = false)
    private String tradingName; // nome fantasia

    @Column(length = 20, unique = true, nullable = false)
    private String ein; // cnpj

    @Column(length = 50, unique = true, nullable = false)
    private String email;

}
