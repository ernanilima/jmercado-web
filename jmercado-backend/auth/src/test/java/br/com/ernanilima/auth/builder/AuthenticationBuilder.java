package br.com.ernanilima.auth.builder;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.auth.LoginDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static br.com.ernanilima.auth.security.JwtUtils.getEncoderAuthentication;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationBuilder {

    public static LoginDTO createLoginDTO() {
        User user = UserBuilder.create();
        return LoginDTO.builder()
                .ein(user.getCompany().getEin())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken() {
        LoginDTO loginDTO = AuthenticationBuilder.createLoginDTO();
        return new UsernamePasswordAuthenticationToken(
                getEncoderAuthentication(loginDTO.getEmail(), loginDTO.getEin()),
                loginDTO.getPassword()
        );
    }

    public static LoginDTO getUserForLogin() {
        return LoginDTO.builder()
                .ein("00000000000191")
                .email("test@ernanilima.com.br")
                .password("123123")
                .build();
    }
}
