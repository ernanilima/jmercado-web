package br.com.ernanilima.auth.security;

import br.com.ernanilima.auth.dto.auth.LoginDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.auth.login.CredentialException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.util.Objects.nonNull;

@Component
@PropertySource("classpath:private_jmercado.properties")
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretWord;
    @Value("${jwt.expiration}")
    private Long expirationTime;
    private final String startToken = "Bearer ";

    public String createToken(LoginDTO dto) {
        return (startToken + JWT.create()
                .withClaim("companyEin", dto.getEin())
                .withSubject(dto.getEmail())
                .withExpiresAt(Instant.now().plus(expirationTime, ChronoUnit.HOURS))
                .sign(HMAC512(secretWord.getBytes())));
    }

    public UsernamePasswordAuthenticationToken getEncoderAuthentication(LoginDTO dto) {
        String emailAndParameter = getEncoderAuthentication(dto.getEmail().trim(), dto.getEin().trim());
        return new UsernamePasswordAuthenticationToken(emailAndParameter, dto.getPassword());
    }

    private String getEncoderAuthentication(String email, String companyEin) {
        return String.format("%s%s%s", email, "-", companyEin);
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

    public String getStartToken() {
        return startToken;
    }

    public boolean isTokenValid(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if (decodedJWT == null) return false;

        String companyEin = decodedJWT.getClaim("companyEin").asString();
        String email = decodedJWT.getSubject();
        Date expirationDate = decodedJWT.getExpiresAt();
        Date currentDate = new Date(System.currentTimeMillis());

        return (nonNull(companyEin) &&
                nonNull(email) &&
                nonNull(expirationDate) &&
                currentDate.before(expirationDate));
    }

    public String getUserEmailAndParameter(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        if (decodedJWT == null) return null;

        String email = decodedJWT.getSubject().trim();
        String companyEin = decodedJWT.getClaim("companyEin").asString().trim();

        return getEncoderAuthentication(email, companyEin);
    }

    private DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(HMAC512(secretWord.getBytes()))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}
