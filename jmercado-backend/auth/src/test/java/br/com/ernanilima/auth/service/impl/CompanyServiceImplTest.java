package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.builder.CompanyBuilder;
import br.com.ernanilima.auth.builder.UserBuilder;
import br.com.ernanilima.auth.config.annotation.UnitTest;
import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserBasicDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@UnitTest
class CompanyServiceImplTest {

    @InjectMocks
    private CompanyServiceImpl companyServiceMock;

    @Mock
    private CompanyRepository companyRepositoryMock;
    @Mock
    private CompanyConverter companyConverterMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private UserConverter userConverterMock;
    @Mock
    private Appender<ILoggingEvent> appenderMock;
    @Captor
    private ArgumentCaptor<ILoggingEvent> argumentCaptor;

    @BeforeEach
    void setup() {
        companyServiceMock = new CompanyServiceImpl(userServiceMock, userConverterMock);
        companyServiceMock.setRepository(companyRepositoryMock);
        companyServiceMock.setConverter(companyConverterMock);

        Logger logger = (Logger) LoggerFactory.getLogger(CompanyServiceImpl.class.getName());
        logger.addAppender(appenderMock);
    }

    @Test
    @DisplayName("Deve retornar a empresa buscada pelo cnpj")
    void findByEin_Must_Return_The_Company_Sought_By_EIN() {
        CompanyDTO companyDTO = CompanyBuilder.createDTO();

        when(companyRepositoryMock.findByEin(any())).thenReturn(Optional.of(CompanyBuilder.create()));
        when(companyConverterMock.toDTO(any())).thenReturn(companyDTO);

        CompanyDTO result = companyServiceMock.findByEin(companyDTO.getEin());

        assertNotNull(result);
        assertThat(result.getId(), is(companyDTO.getId()));
        assertThat(result.getCompanyName(), is(companyDTO.getCompanyName()));
        assertThat(result.getTradingName(), is(companyDTO.getTradingName()));
        assertThat(result.getEin(), is(companyDTO.getEin()));
        assertThat(result.getAddress().getId(), is(companyDTO.getAddress().getId()));
        assertThat(result.getContacts().stream().toList().get(0).getId(), is(companyDTO.getContacts().stream().toList().get(0).getId()));

        verify(companyRepositoryMock, times(1)).findByEin(any());
        verify(companyConverterMock, times(1)).toDTO(any());
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findByEin(obj), iniciando busca da empresa com o cnpj {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(companyServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1], hasToString(companyDTO.getEin()));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findByEin(obj), localizado a empresa com o cnpj {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(companyServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(companyDTO.getEin()));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro para empresa nao encontrada pelo cnpj")
    void findByEin_Must__Return_An_Error_Message_For_Company_Not_Found_By_EIN() throws ObjectNotFoundException {
        String ein = "00000000000272";

        when(companyRepositoryMock.findByEin(any())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> companyServiceMock.findByEin(ein));
        assertThat(exception.getMessage(), is(format(getMessage(OBJECT_NOT_FOUND), getClassName(companyServiceMock.getENTITY().getSimpleName()))));

        verify(companyRepositoryMock, times(1)).findByEin(any());
        verify(appenderMock, times(2)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:findByEin(obj), iniciando busca da empresa com o cnpj {}"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(companyServiceMock.getCLASS_NAME()));
        assertThat(logger0.getArgumentArray()[1], hasToString(ein));

        ILoggingEvent logger1 = argumentCaptor.getAllValues().get(1);
        assertThat(logger1.getMessage(), is("{}:findByEin(obj), erro ao buscar a empresa com o cnpj {}"));
        assertThat(logger1.getArgumentArray()[0].toString(), containsString(companyServiceMock.getCLASS_NAME()));
        assertThat(logger1.getArgumentArray()[1], hasToString(ein));
    }

    @Test
    @DisplayName("Deve enviar o usuario vinculado ao cadastro da empresa")
    void afterInsert_Must_Send_The_User_Linked_To_The_Companys_Registration() {
        CompanyDTO companyDTO = CompanyBuilder.createDTO();
        companyDTO = companyDTO.toBuilder().user(UserBuilder.createBasicDTO()).build();

        when(userConverterMock.toDTO(any(UserBasicDTO.class))).thenReturn(UserBuilder.createDTO());

        companyServiceMock.afterInsert(CompanyBuilder.create(), companyDTO);

        verify(userConverterMock, times(1)).toDTO(any(UserBasicDTO.class));
        verifyNoMoreInteractions(userConverterMock);
        verify(userServiceMock, times(1)).insert(any());
        verifyNoMoreInteractions(userServiceMock);
        verify(appenderMock, times(1)).doAppend(argumentCaptor.capture());

        ILoggingEvent logger0 = argumentCaptor.getAllValues().get(0);
        assertThat(logger0.getMessage(), is("{}:afterInsert(obj), iniciando"));
        assertThat(logger0.getArgumentArray()[0].toString(), containsString(companyServiceMock.getCLASS_NAME()));
    }
}