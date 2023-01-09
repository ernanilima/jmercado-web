package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.param.AuthSecurityLink;
import br.com.ernanilima.auth.service.UserVerificationService;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Get;
import br.com.ernanilima.auth.service.validation.Put;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/usuario-verificacao")
public class UserVerificationResource {

    private final UserVerificationService userVerificationService;

    @GetMapping(value = "/link/{securityLink}")
    public ResponseEntity<UserVerificationDTO> findBySecurityLink(@Validated(Get.class) AuthSecurityLink obj) {
        log.info("{}:get:findBySecurityLink(obj), chamado o endpoint /usuario-verificacao/link/{securityLink}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(userVerificationService.findBySecurityLink(obj.getSecurityLink()));
    }

    @PutMapping(value = "/{securityLink}")
    public ResponseEntity<Message> update(@Validated(Put.class) AuthSecurityLink obj,
                                          @Validated(Put.class) @RequestBody UserVerificationDTO dto) {
        log.info("{}:put:update(obj)", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(userVerificationService.update(obj.getSecurityLink(), dto));
    }
}
