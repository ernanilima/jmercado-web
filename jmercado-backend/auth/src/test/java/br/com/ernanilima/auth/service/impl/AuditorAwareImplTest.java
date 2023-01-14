package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.core.StandardUsers;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditorAwareImplTest {

    private AuditorAwareImpl auditorAwareMock;

    @BeforeEach
    void setup() {
        auditorAwareMock = new AuditorAwareImpl();
    }

    @Test
    @DisplayName("Deve retornar o uuid para usuario nao autenticado")
    void getCurrentAuditor_Must_Return_Uuid_For_Unauthenticated_User() {
        Authentication authentication = mock(AnonymousAuthenticationToken.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Optional<UUID> uuid = auditorAwareMock.getCurrentAuditor();

        assertTrue(uuid.isPresent());
        assertThat(uuid.get(), is(StandardUsers.UNAUTHENTICATED_UUID));
    }

    @Test
    @DisplayName("Deve retornar o uuid para usuario autenticado")
    void getCurrentAuditor_Must_Return_Uuid_For_Authenticated_User() {
        UserSpringSecurity userSpringSecurity = UserBuilder.createUserSpringSecurity();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(userSpringSecurity);
        SecurityContextHolder.setContext(securityContext);

        Optional<UUID> uuid = auditorAwareMock.getCurrentAuditor();

        assertTrue(uuid.isPresent());
        assertThat(uuid.get(), is(userSpringSecurity.getKey()));
    }
}