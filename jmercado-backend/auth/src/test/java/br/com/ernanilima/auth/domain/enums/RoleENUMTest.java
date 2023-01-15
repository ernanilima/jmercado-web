package br.com.ernanilima.auth.domain.enums;

import br.com.ernanilima.auth.config.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
class RoleENUMTest {

    @Test
    @DisplayName("Deve retornar codigo de permissao para o suporte")
    void getRoleSUPPORT_Must_Return_Permission_Code_To_Support() {
        assertEquals(9999, RoleENUM.SUPPORT.getRole().getId());
    }

    @Test
    @DisplayName("Deve retornar codigo de permissao para o administrador")
    void getRoleADMINISTRATOR_Must_Return_Permission_Code_For_Admin() {
        assertEquals(9998, RoleENUM.ADMINISTRATOR.getRole().getId());
    }
}