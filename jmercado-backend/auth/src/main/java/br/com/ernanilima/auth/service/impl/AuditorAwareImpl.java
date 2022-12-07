package br.com.ernanilima.auth.service.impl;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    // TODO: capturar nome do usuario
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Nome Usuário");
    }
}
