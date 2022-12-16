package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.message.Message;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDTO findById(UUID id);

    UserDTO findByEmail(String email);

    List<UserDTO> findAll();

    Message insert(UserDTO dto);

    Message update(UUID id, UserDTO dto);

}
