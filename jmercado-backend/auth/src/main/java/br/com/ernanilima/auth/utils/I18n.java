package br.com.ernanilima.auth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18n {

    public static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    public static final String TTL_VALIDATION = "title.validation";
    public static final String TTL_DATA_INTEGRITY = "title.data.integrity";
    public static final String TTL_AUTHENTICATION_ERROR = "title.authentication.error";
    public static final String TTL_NOT_FOUND = "title.not.found";

    public static final String EXC_QUANTITY_OF_ERRORS = "exc.quantity.of.errors";

    public static final String BAD_CREDENTIALS = "bad.credentials";

    public static final String MESSAGE_SUCCESS_INSERT = "message.success.insert";
    public static final String MESSAGE_SUCCESS_UPDATE = "message.success.update";
    public static final String MESSAGE_SUCCESS_DELETE = "message.success.delete";

    public static final String OBJECT_NOT_FOUND = "object.not.found";
    public static final String VALUE_NOT_FOUND = "value.not.found";
    public static final String INTEGRITY_INSERT_UPDATE = "integrity.insert.update";
    public static final String INTEGRITY_DELETE = "integrity.delete";

    public static String getFieldName(DataIntegrityViolationException e) {
        String exception = e.getMostSpecificCause().getMessage();

        // exemplo: PUBLIC.COMPANY(EIN NULLS FIRST)
        int startField = exception.indexOf("(") + 1; // primeiro '('
        int endField = exception.indexOf(" ", startField); // primeiro ' ' a partir do index informado
        String fieldName = exception.substring(startField, endField); // nome do campo, exemplo: EIN

        return getMessage(fieldName);
    }

    public static String getFieldName(String s) {
        return getMessage(s.replaceAll(".*\\.", ""));
    }

    public static String getClassName(String s) {
        return getMessage(s);
    }

    public static String getMessage(String s) {
        Objects.requireNonNull(s);

        return ResourceBundle.getBundle("message", I18n.DEFAULT_LOCALE)
                .getString(s.toLowerCase());
    }
}
