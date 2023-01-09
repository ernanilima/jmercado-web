package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.param.AuthSecurityLink;
import br.com.ernanilima.auth.service.UserVerificationService;
import br.com.ernanilima.auth.service.validation.Get;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
