package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.message.Message;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserDTO findById(UUID id);

    UserDTO findByEmail(String email);

    User findByEmailAndCompanyEin(String email, String companyEin);

    List<UserDTO> findAll();

    Message insert(UserDTO dto);

    Message update(UUID id, UserDTO dto);

    Message delete(UUID id);

}
