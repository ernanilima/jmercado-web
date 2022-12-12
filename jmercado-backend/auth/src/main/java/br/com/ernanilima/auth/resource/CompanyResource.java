package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> insert(@RequestBody CompanyDTO dto) {
        log.info("{}:post:insert(obj), chamado o endpoint /empresa", this.getClass().getSimpleName());

        companyService.insert(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{idCompany}")
    public ResponseEntity<Void> update(@PathVariable UUID idCompany, @RequestBody CompanyDTO dto) {
        log.info("{}:put:update(obj), chamado o endpoint /empresa/{idCompany}", this.getClass().getSimpleName());

        companyService.update(idCompany, dto);

        return ResponseEntity.noContent().build();
    }
}
