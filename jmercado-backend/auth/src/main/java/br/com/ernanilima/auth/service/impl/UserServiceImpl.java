package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CompanyService companyService;
    private final Message message;

    @Override
    public Message insert(UserDTO dto) {
        log.info("{}:insert(obj), iniciando insercao do usuario com o e-mail {}", CLASS_NAME, dto.getEmail());

        CompanyDTO companyDTO = companyService.findById(dto.getCompany().getId());

        dto = dto.toBuilder().company(companyDTO).build();

        User result = userRepository.save(userConverter.toEntity(dto));

        log.info("{}:insert(obj), inserido o usuario", CLASS_NAME);

        return message.getSuccessInsertForId(result.getId());
    }
}
