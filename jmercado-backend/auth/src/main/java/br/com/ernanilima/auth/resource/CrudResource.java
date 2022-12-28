package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.AuthEntity;
import br.com.ernanilima.auth.dto.DTOUpdate;
import br.com.ernanilima.auth.param.AuthUUID;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Delete;
import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Getter
public abstract class CrudResource<E extends AuthEntity, D extends DTOUpdate>
        extends ReadOnlyResource<E, D> {

    public CrudService<E, D> getCrudService() {
        return (CrudService<E, D>) super.getService();
    }

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody D dto) {
        log.info("{}:post:insert(obj)", this.getClass().getSimpleName());

        Message result = getCrudService().insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> update(@Validated(Put.class) AuthUUID obj,
                                          @Validated(Put.class) @RequestBody D dto) {
        log.info("{}:put:update(obj)", this.getClass().getSimpleName());

        Message result = getCrudService().update(obj.getId(), dto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> delete(@Validated(Delete.class) AuthUUID obj) {
        log.info("{}:delete:delete(obj)", this.getClass().getSimpleName());

        Message result = getCrudService().delete(obj.getId());

        return ResponseEntity.ok().body(result);
    }
}
