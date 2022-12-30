package br.com.ernanilima.auth.domain;

import br.com.ernanilima.auth.core.autopersistence.JsonPersistence;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "role")
@JsonPersistence(jsonFile = "db/jsonpersistence/role.json")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_role", nullable = false, unique = true)
    private int id;

    @Column(name = "id_daddy")
    private Integer idDaddy;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private String type;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_daddy")
    private List<Role> roles = new ArrayList<>();

}
