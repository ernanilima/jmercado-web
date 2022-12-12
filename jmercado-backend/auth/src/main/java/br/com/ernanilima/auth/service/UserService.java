package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.service.message.Message;

public interface UserService {

    Message insert(UserDTO dto);

}
