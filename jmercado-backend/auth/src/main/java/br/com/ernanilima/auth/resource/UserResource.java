package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.param.AuthEmail;
import br.com.ernanilima.auth.service.impl.UserServiceImpl;
import br.com.ernanilima.auth.service.validation.Get;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UserResource extends CrudResource<User, UserDTO, UUID> {

    @Override
    public UserServiceImpl getService() {
        return (UserServiceImpl) super.getService();
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@Validated(Get.class) AuthEmail obj) {
        log.info("{}:get:findByEmail(obj), chamado o endpoint /usuario/email/{email}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(getService().findByEmail(obj.getEmail()));
    }
}
