package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.builder.AuthenticationBuilder;
import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;
import br.com.ernanilima.auth.security.JwtUtils;
import br.com.ernanilima.auth.service.exception.JwtAuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;

import static br.com.ernanilima.auth.utils.I18n.BAD_CREDENTIALS;
import static br.com.ernanilima.auth.utils.I18n.getMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceMock;

    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Mock
    private JwtUtils jwtUtilsMock;
    @Mock
    private HttpServletResponse responseMock;

    @BeforeEach
    void setup() {
        authenticationServiceMock = new AuthenticationServiceImpl(authenticationManagerMock, jwtUtilsMock, responseMock);
    }

    @Test
    @DisplayName("Deve retornar os dados para login realizado")
    void login_Must_Return_Data_For_Login_Performed() {
        LoginDTO loginDTO = AuthenticationBuilder.createLoginDTO();

        when(jwtUtilsMock.getEncoderAuthentication(any())).thenReturn(AuthenticationBuilder.createUsernamePasswordAuthenticationToken());
        when(jwtUtilsMock.createToken(any())).thenReturn("Bearer A!b@C#");

        TokenDTO tokenDTO = authenticationServiceMock.login(loginDTO);

        assertNotNull(tokenDTO);
        assertThat(tokenDTO.getToken(), is("Bearer A!b@C#"));
        assertThat(tokenDTO.getEin(), is(loginDTO.getEin()));
        assertThat(tokenDTO.getEmail(), is(loginDTO.getEmail()));

        verify(jwtUtilsMock, times(1)).getEncoderAuthentication(any());
        verify(jwtUtilsMock, times(1)).createToken(any());
        verifyNoMoreInteractions(jwtUtilsMock);
    }

    @Test
    @DisplayName("Deve retornar um erro para login nao realizado")
    void login_Must__Return_An_Error_For_Failed_Login() throws AuthenticationException {
        LoginDTO loginDTO = AuthenticationBuilder.createLoginDTO();

        when(jwtUtilsMock.getEncoderAuthentication(any())).thenReturn(AuthenticationBuilder.createUsernamePasswordAuthenticationToken());
        doThrow(new BadCredentialsException(getMessage(BAD_CREDENTIALS))).when(authenticationManagerMock).authenticate(any());

        JwtAuthenticationException exception = assertThrows(JwtAuthenticationException.class, () -> authenticationServiceMock.login(loginDTO));
        assertThat(exception.getMessage(), is(getMessage(BAD_CREDENTIALS)));

        verify(jwtUtilsMock, times(1)).getEncoderAuthentication(any());
        verifyNoMoreInteractions(jwtUtilsMock);
        verify(authenticationManagerMock, times(1)).authenticate(any());
        verifyNoMoreInteractions(authenticationManagerMock);
    }
}