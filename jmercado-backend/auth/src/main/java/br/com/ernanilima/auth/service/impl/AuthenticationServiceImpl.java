package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;
import br.com.ernanilima.auth.security.JwtUtils;
import br.com.ernanilima.auth.service.AuthenticationService;
import br.com.ernanilima.auth.service.exception.JwtAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static br.com.ernanilima.auth.utils.I18n.BAD_CREDENTIALS;
import static br.com.ernanilima.auth.utils.I18n.getMessage;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final HttpServletResponse response;

    @Override
    @Transactional(readOnly = true)
    public TokenDTO login(LoginDTO dto) {
        try {
            // obitem os dados para realizar o login
            UsernamePasswordAuthenticationToken authentication = jwtUtils.getEncoderAuthentication(dto);
            // usa o "UserDetailsService" para verificar se o login eh valido
            authenticationManager.authenticate(authentication);

            // se a autenticação for realizada
            String token = jwtUtils.createToken(dto);

            // adiciona o token no cabecalho
            response.addHeader("Authorization", token);
            response.addHeader("access-control-expose-headers", "Authorization");

            return TokenDTO.builder()
                    .token(token)
                    .ein(dto.getEin())
                    .email(dto.getEmail())
                    .build();
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException(getMessage(BAD_CREDENTIALS));
        }
    }
}
