package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.domain.AuthEntity;
import br.com.ernanilima.auth.dto.DTOUpdate;
import br.com.ernanilima.auth.service.exception.DataIntegrityException;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.utils.I18n;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;

@Slf4j
@Getter
public abstract class CrudService<E extends AuthEntity<I>, D extends DTOUpdate, I extends Serializable>
        extends ReadOnlyService<E, D, I> {

    @Autowired
    private Message message;

    public Message insert(D dto) {
        log.info("{}:insert(obj), iniciando insercao", CLASS_NAME);

        dto = beforeInsert(dto);

        E result = this.save(super.getConverter().toEntity(dto));

        log.info("{}:insert(obj), inserido com o id {}", CLASS_NAME, result.getId());

        return message.getSuccessInsertForId((UUID) result.getId());
    }

    protected D beforeInsert(D dto) {
        return dto;
    }

    public Message update(I id, D dto) {
        log.info("{}:update(obj), iniciando atualizacao para o id {}", CLASS_NAME, id);

        super.findById(id);

        dto.setId((UUID) id);
        dto = beforeUpdate(dto);
        this.save(super.getConverter().toEntity(dto));

        log.info("{}:update(obj), atualizado o id {}", CLASS_NAME, id);

        return message.getSuccessUpdateForId((UUID) id);
    }

    protected D beforeUpdate(D dto) {
        return dto;
    }

    private E save(E entity) {
        try {
            return super.getRepository().save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    format(I18n.getMessage(INTEGRITY_INSERT_UPDATE), getFieldName(e))
            );
        }
    }

    public Message delete(I id) {
        log.info("{}:delete(obj), iniciando exclusao para o id {}", CLASS_NAME, id);

        super.findById(id);

        try {
            super.getRepository().deleteById(id);
        } catch (DataIntegrityViolationException e) {
            Class<E> entity = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            throw new DataIntegrityException(
                    format(I18n.getMessage(INTEGRITY_DELETE), getClassName(entity.getSimpleName()))
            );
        }

        log.info("{}:delete(obj), excluido o id {}", CLASS_NAME, id);

        return message.getSuccessDeleteForId((UUID) id);
    }
}