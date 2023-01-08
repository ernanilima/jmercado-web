package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.UserVerificationService;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

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
}
