package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody UserDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /usuario", this.getClass().getSimpleName());

        Message result = userService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idUser}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }
}
