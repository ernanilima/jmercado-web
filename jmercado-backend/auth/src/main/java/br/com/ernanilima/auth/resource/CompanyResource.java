package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
