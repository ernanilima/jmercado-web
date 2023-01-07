package br.com.ernanilima.auth.security;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.auth.login.CredentialException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
@PropertySource("classpath:private_jmercado.properties")
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretWord;
    @Value("${jwt.expiration}")
    private Long expirationTime;
    public String startToken = "Bearer ";

    public String createToken(LoginDTO dto) {
        return (startToken + JWT.create()
                .withClaim("companyEin", dto.getEin())
                .withSubject(dto.getEmail())
                .withExpiresAt(Instant.now().plus(expirationTime, ChronoUnit.HOURS))
                .sign(HMAC512(secretWord.getBytes())));
    }

    public UsernamePasswordAuthenticationToken getEncoderAuthentication(LoginDTO dto) {
        String emailAndParameter = String.format("%s%s%s", dto.getEmail().trim(), "-", dto.getEin());
        return new UsernamePasswordAuthenticationToken(emailAndParameter, dto.getPassword());
    }

    public LoginDTO getDecoderAuthentication(String value) throws CredentialException {
        String[] values = StringUtils.split(value, "-");

        if (values == null || values.length != 2) {
            throw new CredentialException();
        }

        String email = values[0];
        String companyEin = values[1];

        return LoginDTO.builder().email(email).ein(companyEin).build();
    }
}
