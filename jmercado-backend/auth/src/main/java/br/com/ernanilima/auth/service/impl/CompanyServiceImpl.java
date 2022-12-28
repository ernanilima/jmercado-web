package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.CompanyConverter;
import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.repository.CompanyRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.ReadOnlyService;
import br.com.ernanilima.auth.service.exception.DataIntegrityException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyServiceImpl extends ReadOnlyService<Company, CompanyDTO, UUID> implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final Message message;

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
    public Message insert(CompanyDTO dto) {
        log.info("{}:insert(obj), iniciando insercao da empresa com o cnpj {}", CLASS_NAME, dto.getEin());

        Company result = this.save(companyConverter.toEntity(dto));

        log.info("{}:insert(obj), inserido a empresa", CLASS_NAME);

        return message.getSuccessInsertForId(result.getId());
    }

    @Override
    public Message update(UUID id, CompanyDTO dto) {
        log.info("{}:update(obj), iniciando atualizacao da empresa com o id {}", CLASS_NAME, id);

        super.findById(id);

        dto = dto.toBuilder().id(id).build();
        this.save(companyConverter.toEntity(dto));

        log.info("{}:update(obj), atualizado a empresa", CLASS_NAME);

        return message.getSuccessUpdateForId(id);
    }

    private Company save(Company entity) {
        try {
            return companyRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    format(getMessage(INTEGRITY_INSERT_UPDATE), getFieldName(e))
            );
        }
    }

    @Override
    public Message delete(UUID id) {
        log.info("{}:delete(obj), iniciando exclusao da empresa com o id {}", CLASS_NAME, id);

        super.findById(id);

        try {
            companyRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    format(getMessage(INTEGRITY_DELETE), getClassName(Company.class.getSimpleName()))
            );
        }

        log.info("{}:delete(obj), excluido a empresa", CLASS_NAME);

        return message.getSuccessDeleteForId(id);
    }
}
