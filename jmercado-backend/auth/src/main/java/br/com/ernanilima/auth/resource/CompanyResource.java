package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.param.AuthEin;
import br.com.ernanilima.auth.param.AuthUUID;
import br.com.ernanilima.auth.service.impl.CompanyServiceImpl;
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
@RequestMapping("/empresa")
public class CompanyResource extends ReadOnlyResource<Company, CompanyDTO, UUID> {

    @Override
    public CompanyServiceImpl getService() {
        return (CompanyServiceImpl) super.getService();
    }

    @GetMapping(value = "/cnpj/{ein}")
    public ResponseEntity<CompanyDTO> findByEin(@Validated(Get.class) AuthEin obj) {
        log.info("{}:get:findByEin(obj), chamado o endpoint /empresa/cnpj/{ein}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(getService().findByEin(obj.getEin()));
    }

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody CompanyDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /empresa", this.getClass().getSimpleName());

        Message result = getService().insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idCompany}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> update(@Validated(Put.class) AuthUUID obj,
                                          @Validated(Put.class) @RequestBody CompanyDTO dto) {
        log.info("{}:put:update(obj), chamado o endpoint /empresa/{id}", this.getClass().getSimpleName());

        Message result = getService().update(obj.getId(), dto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> delete(@Validated(Delete.class) AuthUUID obj) {
        log.info("{}:delete:delete(obj), chamado o endpoint /empresa/{id}", this.getClass().getSimpleName());

        Message result = getService().delete(obj.getId());

        return ResponseEntity.ok().body(result);
    }
}
