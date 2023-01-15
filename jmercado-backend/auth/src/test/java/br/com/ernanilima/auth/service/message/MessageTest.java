package br.com.ernanilima.auth.service.message;

import br.com.ernanilima.auth.config.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTest
class MessageTest {

    @InjectMocks
    private Message messageMock;

    @Test
    @DisplayName("Deve retornar a mensagem de sucesso ao inserir")
    void getSuccessInsertForId_Must_Return_Success_Message_When_Inserting() {
        UUID uuid = UUID.randomUUID();
        Message messageSuccessInsert = messageMock.getSuccessInsertForId(uuid);

        assertEquals(uuid, messageSuccessInsert.getId());
        assertEquals(getMessage(MESSAGE_SUCCESS_INSERT), messageSuccessInsert.getMessage());
    }

    @Test
    @DisplayName("Deve retornar a mensagem de sucesso ao atualizar")
    void getSuccessInsertForId_Must_Return_Success_Message_When_Updating() {
        UUID uuid = UUID.randomUUID();
        Message messageSuccessUpdate = messageMock.getSuccessUpdateForId(uuid);

        assertEquals(uuid, messageSuccessUpdate.getId());
        assertEquals(getMessage(MESSAGE_SUCCESS_UPDATE), messageSuccessUpdate.getMessage());
    }

    @Test
    @DisplayName("Deve retornar a mensagem de sucesso ao deletar")
    void getSuccessDeleteForId_Must_Return_Success_Message_When_Deleting() {
        UUID uuid = UUID.randomUUID();
        Message messageSuccessDelete = messageMock.getSuccessDeleteForId(uuid);

        assertEquals(uuid, messageSuccessDelete.getId());
        assertEquals(getMessage(MESSAGE_SUCCESS_DELETE), messageSuccessDelete.getMessage());
    }
}