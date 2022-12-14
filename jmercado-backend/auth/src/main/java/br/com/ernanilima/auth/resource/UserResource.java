package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UserResource {

    private final UserService userService;

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID idUser) {
        log.info("{}:get:findById(obj), chamado o endpoint /usuario/{idUser}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(userService.findById(idUser));
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        log.info("{}:get:findByEmail(obj), chamado o endpoint /usuario/email/{email}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        log.info("{}:get:findAll(), chamado o endpoint /usuario", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody UserDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /usuario", this.getClass().getSimpleName());

        Message result = userService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idUser}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }
}
