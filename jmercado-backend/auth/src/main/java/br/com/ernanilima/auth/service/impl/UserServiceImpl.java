package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends CrudService<User, UserDTO> implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;

    @Override
    public UserDTO findByEmail(String email) {
        log.info("{}:findByEmail(obj), iniciando busca do usuario com o e-mail {}", CLASS_NAME, email);

        Optional<User> result = userRepository.findByEmail(email);

        User user = result.orElseThrow(() -> {
            log.error("{}:findByEmail(obj), erro ao buscar o usuario com o e-mail {}", CLASS_NAME, email);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findByEmail(obj), localizado o usuario com o e-mail {}", CLASS_NAME, email);

        return super.getConverter().toDTO(user);
    }

    @Override
    protected UserDTO beforeInsert(UserDTO dto) {
        log.info("{}:beforeInsert(obj), iniciado manipulacao antes da insercao do usuario", CLASS_NAME);

        CompanyDTO companyDTO = companyService.findById(dto.getCompany().getId());

        dto = dto.toBuilder().company(companyDTO).build();

        log.info("{}:beforeInsert(obj), atualizado vinculacao antes da insercao do usuario", CLASS_NAME);

        return dto;
    }

    @Override
    protected UserDTO beforeUpdate(UserDTO dto) {
        log.info("{}:beforeUpdate(obj), iniciado manipulacao antes da atualizar do usuario", CLASS_NAME);

        UserDTO userDTO = super.findById(dto.getId());

        dto = dto.toBuilder().company(userDTO.getCompany()).build();

        log.info("{}:beforeUpdate(obj), atualizado vinculacao antes da atualizar o usuario", CLASS_NAME);

        return dto;
    }
}
