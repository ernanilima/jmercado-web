package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

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
}
