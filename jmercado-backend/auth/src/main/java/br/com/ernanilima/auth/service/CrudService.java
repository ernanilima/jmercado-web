package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.domain.AuthEntity;
import br.com.ernanilima.auth.dto.DTOUpdate;
import br.com.ernanilima.auth.service.exception.DataIntegrityException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.utils.I18n;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;
import static br.com.ernanilima.auth.utils.Utils.getLastValue;
import static java.text.MessageFormat.format;

@Slf4j
@Getter
public abstract class CrudService<E extends AuthEntity, D extends DTOUpdate>
        extends ReadOnlyService<E, D> {

    @Setter
    @Autowired
    private Message message;

    public Message insert(D dto) {
        log.info("{}:insert(obj), iniciando insercao para {}", CLASS_NAME, ENTITY.getSimpleName());

        dto = beforeInsert(dto);

        E result = this.save(super.getConverter().toEntity(dto));

        log.info("{}:insert(obj), inserido {} com o id {}", CLASS_NAME, ENTITY.getSimpleName(), result.getId());

        afterInsert(result, dto);

        return message.getSuccessInsertForId(result.getId());
    }

    protected D beforeInsert(D dto) {
        return dto;
    }

    protected void afterInsert(E entity, D dto) {
    }

    public Message update(UUID id, D dto) {
        log.info("{}:update(obj), iniciando atualizacao de {} com o id {}", CLASS_NAME, ENTITY.getSimpleName(), id);

        super.findById(id);

        dto.setId(id);
        dto = beforeUpdate(dto);
        this.save(super.getConverter().toEntity(dto));

        log.info("{}:update(obj), atualizado {} com id {}", CLASS_NAME, ENTITY.getSimpleName(), id);

        return message.getSuccessUpdateForId(id);
    }

    protected D beforeUpdate(D dto) {
        return dto;
    }

    private E save(E entity) {
        try {
            return super.getRepository().save(entity);
        } catch (DataIntegrityViolationException e) {
            String message = format(I18n.getMessage(INTEGRITY_INSERT_UPDATE), getFieldName(e));
            log.error("{}:save(obj), erro ao inserir/atualizar {}, mensagem {}", CLASS_NAME, ENTITY.getSimpleName(), message);
            throw new DataIntegrityException(message);
        } catch (JpaObjectRetrievalFailureException e) {
            String message = format(I18n.getMessage(VALUE_NOT_FOUND), getLastValue(e));
            log.error("{}:save(obj), erro ao inserir/atualizar {}, mensagem {}", CLASS_NAME, ENTITY.getSimpleName(), message);
            throw new ObjectNotFoundException(message);
        }
    }

    public Message delete(UUID id) {
        log.info("{}:delete(obj), iniciando exclusao de {} com o id {}", CLASS_NAME, ENTITY.getSimpleName(), id);

        super.findById(id);

        try {
            super.getRepository().deleteById(id);
        } catch (DataIntegrityViolationException e) {
            String message = format(I18n.getMessage(INTEGRITY_DELETE), getClassName(super.ENTITY.getSimpleName()));
            log.error("{}:delete(obj), erro ao excluir {}, mensagem {}", CLASS_NAME, ENTITY.getSimpleName(), message);
            throw new DataIntegrityException(message);
        }

        log.info("{}:delete(obj), excluido {} com id {}", CLASS_NAME, ENTITY.getSimpleName(), id);

        return message.getSuccessDeleteForId(id);
    }
}
