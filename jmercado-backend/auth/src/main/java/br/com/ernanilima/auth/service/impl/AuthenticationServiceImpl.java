package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;
import br.com.ernanilima.auth.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public TokenDTO login(LoginDTO dto) {
        return TokenDTO.builder()
                .token("!@#$%^&*()_+")
                .ein(dto.getEin())
                .email(dto.getEmail())
                .build();
    }
}
