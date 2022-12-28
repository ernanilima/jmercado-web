package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.converter.DTOConverter;
import br.com.ernanilima.auth.domain.AuthEntity;
import br.com.ernanilima.auth.dto.DTOUpdate;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Getter
public abstract class ReadOnlyService<E extends AuthEntity, D extends DTOUpdate> {
    protected final String CLASS_NAME = this.getClass().getSimpleName();

    @Autowired
    private JpaRepository<E, UUID> repository;

    @Autowired
    private DTOConverter<E, D> converter;

    public D findById(UUID id) {
        log.info("{}:findById(obj), iniciando busca para o id {}", CLASS_NAME, id);

        Optional<E> result = repository.findById(id);

        E entity = result.orElseThrow(() -> {
            log.error("{}:findById(obj), erro realizar busca para o id {}", CLASS_NAME, id);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findById(obj), localizado o id {}", CLASS_NAME, id);

        return converter.toDTO(entity);
    }

    public List<D> findAll() {
        log.info("{}:findAll(), iniciando busca", CLASS_NAME);

        List<E> results = repository.findAll();

        log.info("{}:findAll(), localizado {} registro(s)", CLASS_NAME, results.size());

        return results.stream().map(converter::toDTO).collect(Collectors.toList());
    }
}
