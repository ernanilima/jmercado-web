package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.AuthEntity;

public interface DTOConverter<E extends AuthEntity<?>, D> {

    E toEntity(D dto);

}
