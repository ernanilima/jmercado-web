package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.converter.UserConverter;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public void insert(UserDTO dto) {
        userRepository.save(userConverter.toEntity(dto));
    }
}
