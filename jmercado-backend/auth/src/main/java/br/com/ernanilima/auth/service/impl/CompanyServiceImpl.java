package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final Message message;

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

    @Override
    public List<CompanyDTO> findAll() {
        log.info("{}:findAll(), iniciando busca de todas as empresa", CLASS_NAME);

        List<Company> results = companyRepository.findAll();

        log.info("{}:findAll(), localizado {} empresa(s)", CLASS_NAME, results.size());

        return results.stream().map(companyConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public Message insert(CompanyDTO dto) {
        log.info("{}:insert(obj), iniciando insercao da empresa com o cnpj {}", CLASS_NAME, dto.getEin());

        Company result = companyRepository.save(companyConverter.toEntity(dto));

        log.info("{}:insert(obj), inserido a empresa", CLASS_NAME);

        return message.getSuccessInsertForId(result.getId());
    }

    @Override
    public Message update(UUID id, CompanyDTO dto) {
        log.info("{}:update(obj), iniciando atualizacao da empresa com o id {}", CLASS_NAME, id);

        this.findById(id);

        dto = dto.toBuilder().id(id).build();
        companyRepository.save(companyConverter.toEntity(dto));

        log.info("{}:update(obj), atualizado a empresa", CLASS_NAME);

        return message.getSuccessUpdateForId(id);
    }

    @Override
    public Message delete(UUID id) {
        log.info("{}:delete(obj), iniciando exclusao da empresa com o id {}", CLASS_NAME, id);

        this.findById(id);

        companyRepository.deleteById(id);

        log.info("{}:delete(obj), excluido a empresa", CLASS_NAME);

        return message.getSuccessDeleteForId(id);
    }
}
