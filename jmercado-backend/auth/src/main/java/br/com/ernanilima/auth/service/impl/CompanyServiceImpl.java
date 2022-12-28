package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl extends CrudService<Company, CompanyDTO, UUID> implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    @Override
    public CompanyDTO findByEin(String ein) {
        log.info("{}:findByEin(obj), iniciando busca da empresa com o cnpj {}", CLASS_NAME, ein);

        Optional<Company> result = companyRepository.findByEin(ein);

        Company company = result.orElseThrow(() -> {
            log.error("{}:findByEin(obj), erro ao buscar a empresa com o cnpj {}", CLASS_NAME, ein);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findByEin(obj), localizado a empresa com o cnpj {}", CLASS_NAME, ein);

        return companyConverter.toDTO(company);
    }
}
