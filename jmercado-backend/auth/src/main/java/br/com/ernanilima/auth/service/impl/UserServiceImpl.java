package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.ReadOnlyService;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ReadOnlyService<User, UserDTO, UUID> implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CompanyService companyService;
    private final Message message;

    @Override
    public UserDTO findByEmail(String email) {
        log.info("{}:findByEmail(obj), iniciando busca do usuario com o e-mail {}", CLASS_NAME, email);

        Optional<User> result = userRepository.findByEmail(email);

        User user = result.orElseThrow(() -> {
            log.error("{}:findByEmail(obj), erro ao buscar o usuario com o e-mail {}", CLASS_NAME, email);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findByEmail(obj), localizado o usuario com o e-mail {}", CLASS_NAME, email);

        return userConverter.toDTO(user);
    }

    @Override
    public Message insert(UserDTO dto) {
        log.info("{}:insert(obj), iniciando insercao do usuario com o e-mail {}", CLASS_NAME, dto.getEmail());

        CompanyDTO companyDTO = companyService.findById(dto.getCompany().getId());

        dto = dto.toBuilder().company(companyDTO).build();

        User result = userRepository.save(userConverter.toEntity(dto));

        log.info("{}:insert(obj), inserido o usuario", CLASS_NAME);

        return message.getSuccessInsertForId(result.getId());
    }

    @Override
    public Message update(UUID id, UserDTO dto) {
        log.info("{}:update(obj), iniciando atualizacao do usuario com o id {}", CLASS_NAME, id);

        UserDTO userDTO = super.findById(id);

        dto = dto.toBuilder().id(id).company(userDTO.getCompany()).build();
        userRepository.save(userConverter.toEntity(dto));

        log.info("{}:update(obj), atualizado o usuario", CLASS_NAME);

        return message.getSuccessUpdateForId(id);
    }

    @Override
    public Message delete(UUID id) {
        log.info("{}:delete(obj), iniciando exclusao do usuario com o id {}", CLASS_NAME, id);

        super.findById(id);

        userRepository.deleteById(id);

        log.info("{}:delete(obj), excluido o usuario", CLASS_NAME);

        return message.getSuccessDeleteForId(id);
    }
}
