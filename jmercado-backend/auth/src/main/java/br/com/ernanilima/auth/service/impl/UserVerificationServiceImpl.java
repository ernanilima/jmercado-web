package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.repository.UserVerificationRepository;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.UserVerificationService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.exception.VerificationException;
import br.com.ernanilima.auth.service.message.Message;
import br.com.ernanilima.auth.utils.I18n;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;

@Slf4j
@Service
@PropertySource("classpath:user_verification.properties")
public class UserVerificationServiceImpl extends CrudService<UserVerification, UserVerificationDTO>
        implements UserVerificationService {

    @Value("${security.link.size}")
    private int securityLinkSize;
    @Value("${security.code.size}")
    private int securityCodeSize;
    @Value("${minutes.to.expiration}")
    private int minutesToExpiration;

    @Override
    public UserVerificationRepository getRepository() {
        return (UserVerificationRepository) super.getRepository();
    }

    @Override
    public UserVerificationDTO findBySecurityLink(String securityLink) {
        log.info("{}:findBySecurityLink(obj), iniciando busca do usuario-verificacao", CLASS_NAME);

        Optional<UserVerification> result = this.getRepository().findBySecurityLink(securityLink);

        UserVerification userVerification = result.orElseThrow(() -> {
            log.error("{}:findBySecurityLink(obj), nao localizado o usuario-verificacao para o link {}", CLASS_NAME, securityLink);
            return new ObjectNotFoundException(
                    format(I18n.getMessage(OBJECT_NOT_FOUND), getClassName(UserVerification.class.getSimpleName()))
            );
        });

        log.info("{}:findBySecurityLink(obj), localizado o usuario-verificacao para o usuario com o e-mail {}", CLASS_NAME, userVerification.getUser().getEmail());

        return super.getConverter().toDTO(userVerification);
    }

    @Override
    protected UserVerificationDTO beforeInsert(UserVerificationDTO dto) {
        log.info("{}:beforeInsert(obj), iniciado manipulacao antes da insercao da verificacao do usuario", CLASS_NAME);

        dto = dto.toBuilder()
                .securityLink(RandomStringUtils.randomAlphabetic(securityLinkSize))
                .securityCode(RandomStringUtils.randomAlphabetic(securityCodeSize))
                .minutesExpiration(minutesToExpiration)
                .build();

        log.info("{}:beforeInsert(obj), atualizado dados antes da insercao da verificacao do usuario", CLASS_NAME);

        return dto;
    }

    @Override
    public Message update(String securityLink, UserVerificationDTO dto) {
        log.info("{}:update(obj), iniciando a verificacao para atualizacao de {}", CLASS_NAME, ENTITY.getSimpleName());

        UserVerificationDTO userVerificationDTO = findBySecurityLink(dto.getSecurityLink());

        if (Objects.equals(dto.getSecurityCode(), userVerificationDTO.getSecurityCode())) {
            log.info("{}:update(obj), verificacao realizada para atualizacao do usuario com o e-mail {}", CLASS_NAME, userVerificationDTO.getUser().getEmail());
            return super.update(userVerificationDTO.getId(), userVerificationDTO.toBuilder().valid(Boolean.TRUE).build());
        }

        log.error("{}:update(obj), verificacao nao realizada com sucesso para o usuario com o e-mail {}", CLASS_NAME, userVerificationDTO.getUser().getEmail());
        throw new VerificationException(I18n.getMessage(VERIFICATION_NOT_SUCCESSFUL));
    }
}
