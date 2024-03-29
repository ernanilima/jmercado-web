package br.com.ernanilima.auth.domain.enums;

import br.com.ernanilima.auth.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleENUM {
    SUPPORT(Role.builder()
            .id(9999)
            .description("Apenas para suporte")
            .type("ROLE")
            .build()),
    ADMINISTRATOR(Role.builder()
            .id(9998)
            .description("Administrador da empresa")
            .type("ROLE")
            .build());

    private final Role role;
}
