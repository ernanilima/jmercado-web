package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.service.validation.Post;
import br.com.ernanilima.auth.service.validation.Put;
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
@RequestMapping("/empresa")
public class CompanyResource {

    private final CompanyService companyService;

    @GetMapping(value = "/{idCompany}")
    public ResponseEntity<CompanyDTO> findById(@PathVariable UUID idCompany) {
        log.info("{}:get:findById(obj), chamado o endpoint /empresa/{idCompany}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(companyService.findById(idCompany));
    }

    @GetMapping(value = "/cnpj/{ein}")
    public ResponseEntity<CompanyDTO> findByEin(@PathVariable String ein) {
        log.info("{}:get:findByEin(obj), chamado o endpoint /empresa/cnpj/{ein}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(companyService.findByEin(ein));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAll() {
        log.info("{}:get:findAll(), chamado o endpoint /empresa", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(companyService.findAll());
    }

    @PostMapping
    public ResponseEntity<Message> insert(@Validated(Post.class) @RequestBody CompanyDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /empresa", this.getClass().getSimpleName());

        Message result = companyService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idCompany}").buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping(value = "/{idCompany}")
    public ResponseEntity<Message> update(@PathVariable UUID idCompany,
                                          @Validated(Put.class) @RequestBody CompanyDTO dto) {
        log.info("{}:put:update(obj), chamado o endpoint /empresa/{idCompany}", this.getClass().getSimpleName());

        Message result = companyService.update(idCompany, dto);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/{idCompany}")
    public ResponseEntity<Message> delete(@PathVariable UUID idCompany) {
        log.info("{}:delete:delete(obj), chamado o endpoint /empresa/{idCompany}", this.getClass().getSimpleName());

        Message result = companyService.delete(idCompany);

        return ResponseEntity.ok().body(result);
    }
}
