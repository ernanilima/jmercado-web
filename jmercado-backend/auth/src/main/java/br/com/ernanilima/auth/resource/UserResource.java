package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDTO dto) {

        userService.insert(dto);

        return ResponseEntity.ok().build();
    }
}
