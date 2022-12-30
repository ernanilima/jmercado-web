package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.utils.I18n;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.ernanilima.auth.utils.I18n.OBJECT_NOT_FOUND;
import static br.com.ernanilima.auth.utils.I18n.getClassName;
import static java.text.MessageFormat.format;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl extends CrudService<Company, CompanyDTO> implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDTO findByEin(String ein) {
        log.info("{}:findByEin(obj), iniciando busca da empresa com o cnpj {}", CLASS_NAME, ein);

        Optional<Company> result = companyRepository.findByEin(ein);

        Company company = result.orElseThrow(() -> {
            log.error("{}:findByEin(obj), erro ao buscar a empresa com o cnpj {}", CLASS_NAME, ein);
            return new ObjectNotFoundException(
                    format(I18n.getMessage(OBJECT_NOT_FOUND), getClassName(Company.class.getSimpleName()))
            );
        });

        log.info("{}:findByEin(obj), localizado a empresa com o cnpj {}", CLASS_NAME, ein);

        return super.getConverter().toDTO(company);
    }
}
