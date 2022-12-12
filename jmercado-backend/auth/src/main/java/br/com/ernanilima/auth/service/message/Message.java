package br.com.ernanilima.auth.service.message;

import br.com.ernanilima.auth.utils.I18n;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;

@Getter
@Component
public class Message {

    private UUID id;
    private String message;

    public Message getSuccessInsertForId(UUID id) {
        this.id = id;
        this.message = I18n.getMessage(MESSAGE_SUCCESS_INSERT);
        return this;
    }

    public Message getSuccessUpdateForId(UUID id) {
        this.id = id;
        this.message = I18n.getMessage(MESSAGE_SUCCESS_UPDATE);
        return this;
    }

    public Message getSuccessDeleteForId(UUID id) {
        this.id = id;
        this.message = I18n.getMessage(MESSAGE_SUCCESS_DELETE);
        return this;
    }
}
