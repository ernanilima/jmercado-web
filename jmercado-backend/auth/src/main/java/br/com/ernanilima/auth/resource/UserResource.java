package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.param.AuthEmail;
import br.com.ernanilima.auth.param.AuthUUID;
import br.com.ernanilima.auth.service.impl.UserServiceImpl;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Delete;
import br.com.ernanilima.auth.service.validation.Get;
import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UserResource extends ReadOnlyResource<User, UserDTO, UUID> {

    @Override
    public UserServiceImpl getService() {
        return (UserServiceImpl) super.getService();
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@Validated(Get.class) AuthEmail obj) {
        log.info("{}:get:findByEmail(obj), chamado o endpoint /usuario/email/{email}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(getService().findByEmail(obj.getEmail()));
    }

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody UserDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /usuario", this.getClass().getSimpleName());

        Message result = getService().insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idUser}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> update(@Validated(Put.class) AuthUUID obj,
                                          @Validated(Put.class) @RequestBody UserDTO dto) {
        log.info("{}:put:update(obj), chamado o endpoint /usuario/{id}", this.getClass().getSimpleName());

        Message result = getService().update(obj.getId(), dto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> delete(@Validated(Delete.class) AuthUUID obj) {
        log.info("{}:delete:delete(obj), chamado o endpoint /usuario/{id}", this.getClass().getSimpleName());

        Message result = getService().delete(obj.getId());

        return ResponseEntity.ok().body(result);
    }
}
