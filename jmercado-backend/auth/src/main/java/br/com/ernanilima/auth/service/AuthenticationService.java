package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;

public interface AuthenticationService {

    TokenDTO login(LoginDTO dto);

}
