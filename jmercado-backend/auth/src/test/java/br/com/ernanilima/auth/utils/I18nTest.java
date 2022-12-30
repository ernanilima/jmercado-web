package br.com.ernanilima.auth.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;
import java.util.MissingResourceException;

import static br.com.ernanilima.auth.utils.I18n.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class I18nTest {

    private DataIntegrityViolationException dataIntegrityViolationException;

    @BeforeEach
    void setup() {
        var sext = new SQLErrorCodeSQLExceptionTranslator();
        var sqlException = new SQLException("PUBLIC.COMPANY(EIN NULLS FIRST)", "44");
        dataIntegrityViolationException = (DataIntegrityViolationException) sext.translate("TASK", "SQL", sqlException);
    }

    @Test
    @DisplayName("Deve retornar um erro para chave invalida para I18n")
    void getFieldNameOrgetMessage_Must_Return_An_Error_For_Invalid_Key_In_I18n() {
        assertThrows(NullPointerException.class, () -> getFieldName((DataIntegrityViolationException) null));

        assertThrows(NullPointerException.class, () -> getFieldName((String) null));
        assertThrows(MissingResourceException.class, () -> getFieldName(""));
        assertThrows(MissingResourceException.class, () -> getFieldName("invalidName"));

        assertThrows(NullPointerException.class, () -> getClassName(null));
        assertThrows(MissingResourceException.class, () -> getClassName(""));
        assertThrows(MissingResourceException.class, () -> getClassName("invalidName"));

        assertThrows(NullPointerException.class, () -> getMessage(null));
        assertThrows(MissingResourceException.class, () -> getMessage(""));
        assertThrows(MissingResourceException.class, () -> getMessage("invalidName"));
    }

    @Test
    @DisplayName("Deve retornar o nome do campo em pt_BR")
    void getFieldName_Must_Return_The_Field_Name_In_PTBR() {
        assertEquals("CNPJ", getFieldName(dataIntegrityViolationException));
        assertEquals("ID", getFieldName("class.id"));
        assertEquals("ID da empresa", getFieldName("company_id"));
        assertEquals("Razão social", getFieldName("company_name"));
        assertEquals("Razão social", getFieldName("companyname"));
        assertEquals("Nome fantasia", getFieldName("trading_name"));
        assertEquals("Nome fantasia", getFieldName("tradingname"));
        assertEquals("E-mail", getFieldName("email"));
        assertEquals("Número de telefone", getFieldName("telephone"));
        assertEquals("Número do celular", getFieldName("cell_phone"));
        assertEquals("Nome", getFieldName("name"));
        assertEquals("Usuário", getFieldName("user"));
        assertEquals("Senha", getFieldName("password"));
        assertEquals("Endereço", getFieldName("address"));
        assertEquals("CEP", getFieldName("zipcode"));
        assertEquals("País", getFieldName("country"));
        assertEquals("Região", getFieldName("region"));
        assertEquals("Estado", getFieldName("state"));
        assertEquals("Cidade", getFieldName("city"));
        assertEquals("Bairro", getFieldName("district"));
        assertEquals("Rua", getFieldName("street"));
        assertEquals("Número", getFieldName("number"));
        assertEquals("IBGE", getFieldName("code"));
        assertEquals("DDD", getFieldName("areacode"));
        assertEquals("Contatos", getFieldName("contacts"));
    }

    @Test
    @DisplayName("Deve retornar o nome da classe em pt_BR")
    void getClassName_Must_Return_The_Class_Name_In_PTBR() {
        assertEquals("Empresa", getClassName("company"));
    }

    @Test
    @DisplayName("Deve retornar a mensagem de validacao em pt_BR")
    void getMessage_Must_Return_The_Validation_Message_In_PTBR() {
        assertEquals("Preenchimento obrigatório", getMessage("empty.field"));
        assertEquals("O campo deve ter entre {min} e {max} caracteres", getMessage("length.field"));
        assertEquals("O campo deve ter {min} caracteres", getMessage("length.min.field"));
        assertEquals("O campo deve ter no máximo {max} caracteres", getMessage("length.max.field"));
        assertEquals("Formato de CNPJ inválido", getMessage("invalid.ein"));
        assertEquals("Formato de e-mail inválido", getMessage("invalid.email"));
        assertEquals("ID inválido", getMessage("invalid.uuid"));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para TTL_VALIDATION")
    void getMessage_Must_Return_The_Message_In_PTBR_To_TTL_VALIDATION() {
        assertEquals("Validação", getMessage(TTL_VALIDATION));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para TTL_DATA_INTEGRITY")
    void getMessage_Must_Return_The_Message_In_PTBR_To_TTL_DATA_INTEGRITY() {
        assertEquals("Integridade de dados", getMessage(TTL_DATA_INTEGRITY));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para TTL_NOT_FOUND")
    void getMessage_Must_Return_The_Message_In_PTBR_To_TTL_NOT_FOUND() {
        assertEquals("Não encontrado", getMessage(TTL_NOT_FOUND));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para EXC_QUANTITY_OF_ERRORS")
    void getMessage_Must_Return_The_Message_In_PTBR_To_EXC_QUANTITY_OF_ERRORS() {
        assertEquals("Quantidade de erro(s): {0}", getMessage(EXC_QUANTITY_OF_ERRORS));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para MESSAGE_SUCCESS_INSERT")
    void getMessage_Must_Return_The_Message_In_PTBR_To_MESSAGE_SUCCESS_INSERT() {
        assertEquals("Registro inserido com sucesso", getMessage(MESSAGE_SUCCESS_INSERT));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para MESSAGE_SUCCESS_UPDATE")
    void getMessage_Must_Return_The_Message_In_PTBR_To_MESSAGE_SUCCESS_UPDATE() {
        assertEquals("Registro atualizado com sucesso", getMessage(MESSAGE_SUCCESS_UPDATE));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para MESSAGE_SUCCESS_DELETE")
    void getMessage_Must_Return_The_Message_In_PTBR_To_MESSAGE_SUCCESS_DELETE() {
        assertEquals("Registro excluido com sucesso", getMessage(MESSAGE_SUCCESS_DELETE));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para OBJECT_NOT_FOUND")
    void getMessage_Must_Return_The_Message_In_PTBR_To_OBJECT_NOT_FOUND() {
        assertEquals("{0} não encontrado(a)", getMessage(OBJECT_NOT_FOUND));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para VALUE_NOT_FOUND")
    void getMessage_Must_Return_The_Message_In_PTBR_To_VALUE_NOT_FOUND() {
        assertEquals("Não foi possível encontrar o valor `{0}`", getMessage(VALUE_NOT_FOUND));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para INTEGRITY_INSERT_UPDATE")
    void getMessage_Must_Return_The_Message_In_PTBR_To_INTEGRITY_INSERT_UPDATE() {
        assertEquals("O valor do campo `{0}` já está sendo usado", getMessage(INTEGRITY_INSERT_UPDATE));
    }

    @Test
    @DisplayName("Deve retornar a mensagem em pt_BR para INTEGRITY_DELETE")
    void getMessage_Must_Return_The_Message_In_PTBR_To_INTEGRITY_DELETE() {
        assertEquals("{0} possui vinculações, impossível remover", getMessage(INTEGRITY_DELETE));
    }
}