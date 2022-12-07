package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/empresa")
public class CompanyResource {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody CompanyDTO dto) {

        companyService.insert(dto);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{idCompany}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable UUID idCompany, @RequestBody CompanyDTO dto) {

        companyService.update(idCompany, dto);

        return ResponseEntity.noContent().build();
    }
}
