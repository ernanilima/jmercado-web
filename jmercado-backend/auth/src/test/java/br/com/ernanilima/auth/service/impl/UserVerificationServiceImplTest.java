package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.config.annotation.UnitTest;
import br.com.ernanilima.auth.converter.UserVerificationConverter;
import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.repository.UserVerificationRepository;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.exception.VerificationException;
import br.com.ernanilima.auth.service.message.Message;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@UnitTest
class UserVerificationServiceImplTest {

    @InjectMocks
    private UserVerificationServiceImpl userVerificationServiceMock;

    @Mock
    private UserVerificationRepository userVerificationRepositoryMock;
    @Mock
    private UserVerificationConverter userVerificationConverterMock;
    @InjectMocks
    private Message messageMock;
    @Mock
    private Appender<ILoggingEvent> appenderMock;
    @Captor
    private ArgumentCaptor<ILoggingEvent> argumentCaptor;

    @BeforeEach
    void setup() {
        userVerificationServiceMock.setRepository(userVerificationRepositoryMock);
        userVerificationServiceMock.setConverter(userVerificationConverterMock);
        userVerificationServiceMock.setMessage(messageMock);
        setField(userVerificationServiceMock, "securityLinkSize", 255);
        setField(userVerificationServiceMock, "securityCodeSize", 6);
        setField(userVerificationServiceMock, "minutesToExpiration", 30);

        Logger logger = (Logger) LoggerFactory.getLogger(UserVerificationServiceImpl.class.getName());
        logger.addAppender(appenderMock);
    }

    @Test
    @DisplayName("Deve retornar os dados para link no prazo e que nao foi validado")
    void findBySecurityLink_Must__Return_The_Data_For_The_Link_On_Time_And_That_Has_Not_Been_Validated() {
        UserVerificationDTO userVerificationDTO = UserBuilder.createVerificationDTO();

        when(userVerificationRepositoryMock.findBySecurityLink(any())).thenReturn(Optional.of(UserBuilder.createVerification()));
        when(userVerificationConverterMock.toDTO(any(UserVerification.class))).thenReturn(userVerificationDTO);

        UserVerificationDTO result = userVerificationServiceMock.findBySecurityLink(userVerificationDTO.getSecurityLink());

        assertNotNull(result);
        assertThat(result.getId(), is(userVerificationDTO.getId()));
        assertThat(result.getUser().getId(), is(userVerificationDTO.getUser().getId()));
        assertThat(result.getSecurityLink(), is(userVerificationDTO.getSecurityLink()));
        assertThat(result.getSecurityCode(), is(userVerificationDTO.getSecurityCode()));
        assertThat(result.getMinutesExpiration(), is(userVerificationDTO.getMinutesExpiration()));
        assertThat(result.isChecked(), is(userVerificationDTO.isChecked()));

        verify(userVerificationRepositoryMock, times(1)).findBySecurityLink(any());
        verify(userVerificationConverterMock, times(1)).toDTO(any(UserVerification.class));
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findBySecurityLink(obj), iniciando busca do usuario-verificacao"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findBySecurityLink(obj), localizado o usuario-verificacao para o usuario com o e-mail {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(result.getUser().getEmail()));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro para link vencido e/ou ja validado")
    void findBySecurityLink_Must_Return_An_Error_Message_For_An_Expired_And_Or_Already_Validated_Link() throws ObjectNotFoundException {
        String link = "Link_Vencido_Ou_Ja_Validado";

        when(userVerificationRepositoryMock.findBySecurityLink(any())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> userVerificationServiceMock.findBySecurityLink(link));
        assertThat(exception.getMessage(), is(format(getMessage(OBJECT_NOT_FOUND), getClassName(userVerificationServiceMock.getENTITY().getSimpleName()))));

        verify(userVerificationRepositoryMock, times(1)).findBySecurityLink(any());
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findBySecurityLink(obj), iniciando busca do usuario-verificacao"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findBySecurityLink(obj), nao localizado o usuario-verificacao para o link {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(link));
    }

    @Test
    @DisplayName("Deve retornar as alteracoes antes de inserir a verificacao do usuario")
    void beforeInsert_Must_Return_Changes_Before_Entering_User_Check() {
        UserVerificationDTO userVerificationDTO = UserVerificationDTO.builder()
                .user(UserBuilder.createDTO())
                .build();

        UserVerificationDTO result = userVerificationServiceMock.beforeInsert(userVerificationDTO);

        assertNotNull(result);
        assertNull(result.getId());
        assertThat(result.getUser().getId(), is(userVerificationDTO.getUser().getId()));
        assertThat(result.getSecurityLink().length(), is(255));
        assertThat(result.getSecurityCode().length(), is(6));
        assertThat(result.getMinutesExpiration(), is(30));
        assertFalse(result.isChecked());

        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:beforeInsert(obj), iniciado manipulacao antes da insercao da verificacao do usuario"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:beforeInsert(obj), atualizado dados antes da insercao da verificacao do usuario"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de sucesso na atualizacao da verificacao do usuario")
    void update_Must_Return_A_Success_Message_On_User_Verification_Update() {
        UserVerification userVerification = UserBuilder.createVerification();
        UserVerificationDTO userVerificationDTO = UserBuilder.createVerificationDTO();

        when(userVerificationRepositoryMock.findBySecurityLink(any())).thenReturn(Optional.of(userVerification));
        when(userVerificationConverterMock.toDTO(any(UserVerification.class))).thenReturn(userVerificationDTO);
        when(userVerificationRepositoryMock.findById(any())).thenReturn(Optional.of(userVerification));

        Message result = userVerificationServiceMock.update(userVerificationDTO.getSecurityLink(), userVerificationDTO);

        assertNotNull(result);
        assertThat(result.getId(), is(userVerificationDTO.getId()));
        assertThat(result.getMessage(), is(messageMock.getSuccessUpdateForId(userVerificationDTO.getId()).getMessage()));

        verify(userVerificationRepositoryMock, times(1)).findBySecurityLink(any());
        verify(userVerificationConverterMock, times(2)).toDTO(any(UserVerification.class));
        verify(userVerificationRepositoryMock, times(1)).findById(any());
        verify(appenderMock, times(4)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:update(obj), iniciando a verificacao para atualizacao de {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1].toString(), containsString(userVerificationServiceMock.getENTITY().getSimpleName()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findBySecurityLink(obj), iniciando busca do usuario-verificacao"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));

        ILoggingEvent logger2 = argumentCaptor.getAllValues().get(2);
        assertThat(logger2.getMessage(), is("{}:findBySecurityLink(obj), localizado o usuario-verificacao para o usuario com o e-mail {}"));
        assertThat(logger2.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger2.getArgumentArray()[1].toString(), containsString(userVerification.getUser().getEmail()));

        ILoggingEvent logger3 = argumentCaptor.getAllValues().get(3);
        assertThat(logger3.getMessage(), is("{}:update(obj), verificacao realizada para atualizacao do usuario com o e-mail {}"));
        assertThat(logger3.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger3.getArgumentArray()[1].toString(), containsString(userVerificationDTO.getUser().getEmail()));
    }

    @Test
    @DisplayName("Deve retornar um erro para verificacao do usuario nao realizada com sucesso")
    void update_Mus_Return_An_Error_For_User_Verification_Not_Successfully_Performed() throws VerificationException {
        UserVerificationDTO userVerificationDTO_Send = UserBuilder.createVerificationDTO().toBuilder()
                .securityCode("codigo_incorreto")
                .build();

        UserVerification userVerification_Db = UserBuilder.createVerification();
        UserVerificationDTO userVerificationDTO_Db = UserBuilder.createVerificationDTO();

        when(userVerificationRepositoryMock.findBySecurityLink(any())).thenReturn(Optional.of(userVerification_Db));
        when(userVerificationConverterMock.toDTO(any(UserVerification.class))).thenReturn(userVerificationDTO_Db);

        VerificationException exception = assertThrows(VerificationException.class, () ->
                userVerificationServiceMock.update(userVerificationDTO_Send.getSecurityLink(), userVerificationDTO_Send));
        assertThat(exception.getMessage(), is(getMessage(VERIFICATION_NOT_SUCCESSFUL)));

        verify(userVerificationRepositoryMock, times(1)).findBySecurityLink(any());
        verify(userVerificationConverterMock, times(1)).toDTO(any(UserVerification.class));
        verify(appenderMock, times(4)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:update(obj), iniciando a verificacao para atualizacao de {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1].toString(), containsString(userVerificationServiceMock.getENTITY().getSimpleName()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findBySecurityLink(obj), iniciando busca do usuario-verificacao"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));

        ILoggingEvent logger2 = argumentCaptor.getAllValues().get(2);
        assertThat(logger2.getMessage(), is("{}:findBySecurityLink(obj), localizado o usuario-verificacao para o usuario com o e-mail {}"));
        assertThat(logger2.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger2.getArgumentArray()[1].toString(), containsString(userVerification_Db.getUser().getEmail()));

        ILoggingEvent logger3 = argumentCaptor.getAllValues().get(3);
        assertThat(logger3.getMessage(), is("{}:update(obj), verificacao nao realizada com sucesso para o usuario com o e-mail {}"));
        assertThat(logger3.getArgumentArray()[0].toString(), containsString(userVerificationServiceMock.getCLASS_NAME()));
        assertThat(logger3.getArgumentArray()[1], hasToString(userVerificationDTO_Send.getUser().getEmail()));
    }
}