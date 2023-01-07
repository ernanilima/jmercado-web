package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.security.JwtUtils;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.DecoderException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String values) throws UsernameNotFoundException {
        String CLASS_NAME = this.getClass().getSimpleName();
        log.info("{}:loadUserByUsername(obj), iniciado login", CLASS_NAME);

        LoginDTO dto;

        try {
            dto = jwtUtils.getDecoderAuthentication(values);
        } catch (CredentialException e) {
            log.error("{}:loadUserByUsername(obj), erro ao decodificar {}", CLASS_NAME, values);
            throw new DecoderException("Dados invalidos para login");
        }

        User user = userService.findByEmailAndCompanyEin(dto.getEmail(), dto.getEin());

        log.info("{}:loadUserByUsername(obj), usuario localizado para login", CLASS_NAME);
        return new UserSpringSecurity(user.getCompany().getEin(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}
