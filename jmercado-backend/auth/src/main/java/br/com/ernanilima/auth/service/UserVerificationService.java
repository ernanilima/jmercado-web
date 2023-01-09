package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.service.message.Message;

import java.util.UUID;

public interface UserVerificationService {

    UserVerificationDTO findBySecurityLink(String securityLink);

    Message insert(UserVerificationDTO dto);

    Message update(String securityLink, UserVerificationDTO dto);

    Message update(UUID id, UserVerificationDTO dto);

}
