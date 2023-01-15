package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.builder.AuthenticationBuilder;
import br.com.ernanilima.auth.builder.CompanyBuilder;
import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.security.JwtUtils;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.UserVerificationService;
import br.com.ernanilima.auth.service.exception.DecoderException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.CredentialException;
import java.util.Optional;
import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceMock;

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserConverter userConverterMock;
    @Mock
    private UserVerificationService userVerificationServiceMock;
    @Mock
    private CompanyService companyServiceMock;
    @Mock
    private BCryptPasswordEncoder passwordEncoderMock;
    @Mock
    private JwtUtils jwtUtilsMock;
    @Mock
    private Appender<ILoggingEvent> appenderMock;
    @Captor
    private ArgumentCaptor<ILoggingEvent> argumentCaptor;

    private static final UserSpringSecurity userSpringSecurity = UserBuilder.createUserSpringSecurity();

    @BeforeAll
    static void setupAll() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(userSpringSecurity);
        SecurityContextHolder.setContext(securityContext);
    }

    @BeforeEach
    void setup() {
        userServiceMock = new UserServiceImpl(userVerificationServiceMock, companyServiceMock, passwordEncoderMock, jwtUtilsMock);
        userServiceMock.setRepository(userRepositoryMock);
        userServiceMock.setConverter(userConverterMock);

        Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class.getName());
        logger.addAppender(appenderMock);
    }

    @Test
    @DisplayName("Deve retornar o usuario buscada pelo e-mail e cnpj do titular do token")
    void findByEmail_Must_Return_The_User_Sought_By_Email_And_Token_Holders_EIN() {
        UserDTO userDTO = UserBuilder.createDTO();

        when(userRepositoryMock.findByEmailAndCompany_Ein(any(), any())).thenReturn(Optional.of(UserBuilder.create()));
        when(userConverterMock.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceMock.findByEmail(userDTO.getEmail());

        assertNotNull(result);
        assertThat(result.getId(), is(userDTO.getId()));
        assertThat(result.getName(), is(userDTO.getName()));
        assertThat(result.getEmail(), is(userDTO.getEmail()));
        assertThat(result.getRoles().stream().toList().get(0).getId(), is(9999));
        assertThat(result.getCompany().getId(), is(userDTO.getCompany().getId()));

        verify(userRepositoryMock, times(1)).findByEmailAndCompany_Ein(any(), any());
        verify(userConverterMock, times(1)).toDTO(any(User.class));
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findByEmailAndCompanyEin(obj), iniciando busca do usuario com o e-mail {} para a empresa {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1], hasToString(userDTO.getEmail()));
        assertThat(logger0.getArgumentArray()[2], hasToString(userSpringSecurity.getCompanyEin()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findByEmailAndCompanyEin(obj), localizado o usuario com o e-mail {} para a empresa {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(userDTO.getEmail()));
        assertThat(logger1.getArgumentArray()[2], hasToString(userSpringSecurity.getCompanyEin()));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro para usario nao encontrada pelo e-mail e cnpj do titular do token")
    void findByEmail_Must_Return_An_Error_Message_For_User_Not_Found_By_Email_And_Token_Holders_EIN() throws ObjectNotFoundException {
        String email = "email.user@email.com";

        when(userRepositoryMock.findByEmailAndCompany_Ein(any(), any())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> userServiceMock.findByEmail(email));
        assertThat(exception.getMessage(), is(format(getMessage(OBJECT_NOT_FOUND), getClassName(userServiceMock.getENTITY().getSimpleName()))));

        verify(userRepositoryMock, times(1)).findByEmailAndCompany_Ein(any(), any());
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findByEmailAndCompanyEin(obj), iniciando busca do usuario com o e-mail {} para a empresa {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1], hasToString(email));
        assertThat(logger0.getArgumentArray()[2], hasToString(userSpringSecurity.getCompanyEin()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findByEmailAndCompanyEin(obj), nao localizado o usuario com o e-mail {} para a empresa {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(email));
        assertThat(logger1.getArgumentArray()[2], hasToString(userSpringSecurity.getCompanyEin()));
    }

    @Test
    @DisplayName("Deve retornar as alteracoes antes de inserir o usuario")
    void beforeInsert_Must_Return_Changes_Before_Inserting_User() {
        UserDTO userDTO = UserBuilder.createDTO();
        userDTO = userDTO.toBuilder().company(CompanyDTO.builder().id(UUID.randomUUID()).build()).build();

        CompanyDTO companyDTO = CompanyBuilder.createDTO();

        when(passwordEncoderMock.encode(any())).thenReturn("coded_password");
        when(companyServiceMock.findById(any())).thenReturn(companyDTO);

        UserDTO result = userServiceMock.beforeInsert(userDTO);

        assertNotNull(result);
        assertThat(result.getId(), is(userDTO.getId()));
        assertThat(result.getPassword(), is(not(userDTO.getPassword())));
        assertThat(result.getPassword(), is("coded_password"));
        assertThat(result.getCompany().getEin(), is(companyDTO.getEin()));

        verify(passwordEncoderMock, times(1)).encode(any());
        verify(companyServiceMock, times(1)).findById(any());
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:beforeInsert(obj), iniciado manipulacao antes da insercao do usuario"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:beforeInsert(obj), atualizado vinculacao antes da insercao do usuario"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
    }

    @Test
    @DisplayName("Deve executar algumas acoes depois de inserir o usuario")
    void afterInsert_Must_Perform_Some_Actions_After_Entering_User() {
        when(userConverterMock.toDTO(any(User.class))).thenReturn(UserBuilder.createDTO());

        userServiceMock.afterInsert(UserBuilder.create(), UserBuilder.createDTO());

        verify(userConverterMock, times(1)).toDTO(any(User.class));
        verifyNoMoreInteractions(userConverterMock);
        verify(userVerificationServiceMock, times(1)).insert(any());
        verifyNoMoreInteractions(userVerificationServiceMock);
        verify(appenderMock, times(1)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:afterInsert(obj), iniciando"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
    }

    @Test
    @DisplayName("Deve executar algumas acoes antes de atualizar o usuario")
    void beforeUpdate_Must_Perform_Some_Actions_Before_Updating_User() {
        UserDTO userDTO = UserBuilder.createDTO();
        userDTO = userDTO.toBuilder().company(null).build();

        when(userRepositoryMock.findById(any())).thenReturn(Optional.of(UserBuilder.create()));
        when(userConverterMock.toDTO(any(User.class))).thenReturn(UserBuilder.createDTO());

        UserDTO result = userServiceMock.beforeUpdate(userDTO);

        assertNotNull(result);
        assertThat(result.getId(), is(userDTO.getId()));
        assertNotNull(result.getCompany());

        verify(userRepositoryMock, times(1)).findById(any());
        verify(userConverterMock, times(1)).toDTO(any(User.class));
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:beforeUpdate(obj), iniciado manipulacao antes da atualizar do usuario"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:beforeUpdate(obj), atualizado vinculacao antes da atualizar o usuario"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
    }

    @Test
    @DisplayName("Deve retornar os dados do usuario autenticado")
    void loadUserByUsername_Must_Return_Authenticated_User_Data() throws CredentialException {
        User user = UserBuilder.create();

        when(jwtUtilsMock.getDecoderAuthentication(any())).thenReturn(AuthenticationBuilder.createLoginDTO());
        when(userRepositoryMock.findByEmailAndCompany_Ein(any(), any())).thenReturn(Optional.of(user));

        UserSpringSecurity result = (UserSpringSecurity) userServiceMock.loadUserByUsername("email.user@email.com-00000000000191");

        assertNotNull(result);
        assertThat(result.getKey(), is(user.getId()));
        assertThat(result.getCompanyEin(), is(user.getCompany().getEin()));
        assertThat(result.getUsername(), is(user.getEmail()));
        assertThat(result.getPassword(), is(user.getPassword()));
        assertThat(result.getAuthorities().size(), is(user.getRoles().size()));

        verify(jwtUtilsMock, times(1)).getDecoderAuthentication(any());
        verifyNoMoreInteractions(jwtUtilsMock);
        verify(userRepositoryMock, times(1)).findByEmailAndCompany_Ein(any(), any());
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    @DisplayName("Deve retornar um erro para usuario nao autenticado")
    void loadUserByUsername_Must_Return_An_Error_For_Unauthenticated_User() throws CredentialException {
        String values = "email.user@email.com-00000000000191";

        doThrow(new CredentialException()).when(jwtUtilsMock).getDecoderAuthentication(any());

        DecoderException exception = assertThrows(DecoderException.class, () -> userServiceMock.loadUserByUsername(values));
        assertThat(exception.getMessage(), is("Dados invalidos para login"));

        verify(jwtUtilsMock, times(1)).getDecoderAuthentication(any());
        verify(appenderMock, times(1)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:loadUserByUsername(obj), erro ao decodificar {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1].toString(), containsString(values));
    }
}