package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.param.AuthEin;
import br.com.ernanilima.auth.service.impl.CompanyServiceImpl;
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
@RequestMapping("/empresa")
public class CompanyResource extends CrudResource<Company, CompanyDTO, UUID> {

    @Override
    public CompanyServiceImpl getService() {
        return (CompanyServiceImpl) super.getService();
    }

    @GetMapping(value = "/cnpj/{ein}")
    public ResponseEntity<CompanyDTO> findByEin(@Validated(Get.class) AuthEin obj) {
        log.info("{}:get:findByEin(obj), chamado o endpoint /empresa/cnpj/{ein}", this.getClass().getSimpleName());
        return ResponseEntity.ok().body(getService().findByEin(obj.getEin()));
    }
}
