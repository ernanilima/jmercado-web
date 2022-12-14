package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.service.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public UserDTO findById(UUID id) {
        log.info("{}:findById(obj), iniciando busca do usuario com o id {}", CLASS_NAME, id);

        Optional<User> result = userRepository.findById(id);

        User user = result.orElseThrow(() -> {
            log.error("{}:findById(obj), erro ao buscar o usuario com o id {}", CLASS_NAME, id);
            return new ObjectNotFoundException("Não encontrado");
        });

        log.info("{}:findById(obj), localizado o usuario com o id {}", CLASS_NAME, id);

        return userConverter.toDTO(user);
    }

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
    public List<UserDTO> findAll() {
        log.info("{}:findAll(), iniciando busca de todos os usuarios", CLASS_NAME);

        List<User> results = userRepository.findAll();

        log.info("{}:findAll(), localizado {} usuario(s)", CLASS_NAME, results.size());

        return results.stream().map(userConverter::toDTO).collect(Collectors.toList());
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
}
