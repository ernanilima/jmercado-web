package br.com.ernanilima.auth.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StandardUsersTest {

    @Test
    @DisplayName("Deve retornar o uuid para usuario nao logado")
    void getUUID_UNAUTHENTICATED_UUID_Must_Return_Uuid_For_User_Not_Logged_In() {
        assertNotNull(StandardUsers.UNAUTHENTICATED_UUID);
    }
}