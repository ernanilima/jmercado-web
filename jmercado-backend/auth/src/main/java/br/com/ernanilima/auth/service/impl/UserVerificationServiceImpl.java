package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.UserVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserVerificationServiceImpl extends CrudService<UserVerification, UserVerificationDTO> implements UserVerificationService {

}
