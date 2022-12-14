package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.AuthEntity;
import br.com.ernanilima.auth.dto.DTOUpdate;
import br.com.ernanilima.auth.param.AuthUUID;
import br.com.ernanilima.auth.service.ReadOnlyService;
import br.com.ernanilima.auth.service.validation.Get;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Getter
public abstract class ReadOnlyResource<E extends AuthEntity, D extends DTOUpdate> {

    @Autowired
    private ReadOnlyService<E, D> service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<D> findById(@Validated(Get.class) AuthUUID obj) {
        log.info("{}:get:findById(obj)", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(service.findById(obj.getId()));
    }

    @GetMapping
    public ResponseEntity<List<D>> findAll() {
        log.info("{}:get:findAll()", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(service.findAll());
    }
}
