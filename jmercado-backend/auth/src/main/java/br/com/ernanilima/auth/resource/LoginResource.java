package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;
import br.com.ernanilima.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class LoginResource {

    private final AuthenticationService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        log.info("{}:post:login(obj), chamado o endpoint /auth/login", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(authService.login(dto));
    }
}
