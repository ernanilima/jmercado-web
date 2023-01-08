package br.com.ernanilima.auth.resource.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.ernanilima.auth.utils.I18n.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    /**
     * Erro para token invalido
     */
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException arg) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String errorTitle = getMessage(TTL_AUTHENTICATION_ERROR);
        String message = getMessage(INVALID_TOKEN);
        StandardError standardError = StandardError.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status(UNAUTHORIZED.value())
                .error(errorTitle)
                .message(message)
                .path(req.getRequestURI())
                .build();

        OutputStream outputStream = res.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, standardError);
        outputStream.flush();
    }
}
