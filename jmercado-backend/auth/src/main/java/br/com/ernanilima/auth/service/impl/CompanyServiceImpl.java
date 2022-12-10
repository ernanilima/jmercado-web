package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    @Override
    public void insert(CompanyDTO dto) {
        companyRepository.save(companyConverter.toEntity(dto));
    }

    @Override
    public void update(UUID id, CompanyDTO dto) {
        dto = dto.toBuilder().id(id).build();
        companyRepository.save(companyConverter.toEntity(dto));
    }

    @Override
    public CompanyDTO findById(UUID id) {
        log.info("{}:findById(obj), iniciando busca da empresa com o id {}", CLASS_NAME, id);

        Optional<Company> result = companyRepository.findById(id);

        Company company = result.orElseThrow(() -> {
            log.error("{}:findById(obj), erro ao buscar a empresa com o id {}", CLASS_NAME, id);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findById(obj), localizado a empresa com o id {}", CLASS_NAME, id);
        return companyConverter.toDTO(company);
    }
}
