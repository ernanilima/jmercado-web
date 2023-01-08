package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.service.message.Message;

import java.util.UUID;

public interface UserVerificationService {

    Message insert(UserVerificationDTO dto);

    Message update(UUID id, UserVerificationDTO dto);

}
