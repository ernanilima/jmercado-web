package br.com.ernanilima.auth.resource.exception;

import br.com.ernanilima.auth.service.exception.DataIntegrityException;
import br.com.ernanilima.auth.service.exception.JwtAuthenticationException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.ernanilima.auth.utils.I18n.*;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    /**
     * Erros de validacao
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                         HttpServletRequest r) {
        log.warn("{}:methodArgumentNotValidException(obj), alerta de validacao em '{}' campo(s)", CLASS_NAME, e.getErrorCount());

        ErrorMultipleFields standardError = validException(e, r);

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(standardError);
    }

    /**
     * Erro de validacao
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<StandardError> bindException(BindException e,
                                                       HttpServletRequest r) {
        log.warn("{}:bindException(obj)", CLASS_NAME);

        ErrorMultipleFields standardError = validException(e, r);

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(standardError);
    }

    private ErrorMultipleFields validException(BindException e,
                                               HttpServletRequest r) {
        log.warn("{}:validException(obj)", CLASS_NAME);

        String errorTitle = getMessage(TTL_VALIDATION);
        String message = format(getMessage(EXC_QUANTITY_OF_ERRORS), e.getErrorCount());
        ErrorMultipleFields standardError = ErrorMultipleFields.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status(UNPROCESSABLE_ENTITY.value())
                .error(errorTitle)
                .message(message)
                .path(r.getRequestURI())
                .build();

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                standardError.addError(getFieldName(fieldError.getField()), fieldError.getDefaultMessage()));

        return standardError;
    }

    /**
     * Erro para integridade de dados
     */
    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException e,
                                                                HttpServletRequest r) {
        log.warn("{}:dataIntegrityException(obj)", CLASS_NAME);

        String errorTitle = getMessage(TTL_DATA_INTEGRITY);
        StandardError standardError = StandardError.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status(BAD_REQUEST.value())
                .error(errorTitle)
                .message(e.getMessage())
                .path(r.getRequestURI())
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(standardError);
    }

    /**
     * Erro para busca nao encontrada
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e,
                                                                 HttpServletRequest r) {
        log.warn("{}:objectNotFoundException(obj)", CLASS_NAME);

        String errorTitle = getMessage(TTL_NOT_FOUND);
        StandardError standardError = StandardError.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status(NOT_FOUND.value())
                .error(errorTitle)
                .message(e.getMessage())
                .path(r.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(standardError);
    }

    /**
     * Erro para login nao realizado
     */
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<StandardError> jwtAuthenticationException(JwtAuthenticationException e,
                                                                    HttpServletRequest r) {
        log.warn("{}:jwtAuthenticationException(obj)", CLASS_NAME);

        String errorTitle = getMessage(TTL_AUTHENTICATION_ERROR);
        StandardError standardError = StandardError.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status(UNAUTHORIZED.value())
                .error(errorTitle)
                .message(e.getMessage())
                .path(r.getRequestURI())
                .build();

        return ResponseEntity.status(UNAUTHORIZED).body(standardError);
    }
}
