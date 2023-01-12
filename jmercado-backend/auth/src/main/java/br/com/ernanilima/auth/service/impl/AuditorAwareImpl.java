package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.security.UserSpringSecurity;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<UUID> {

    @NonNull
    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.of(UserSpringSecurity.getAuthenticatedUser().getKey());
    }
}
