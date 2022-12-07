package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.JMercadoEntity;

public interface DTOConverter<E extends JMercadoEntity<?>, D> {

    E toEntity(D dto);

}
