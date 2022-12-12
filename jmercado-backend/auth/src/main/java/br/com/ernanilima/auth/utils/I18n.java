package br.com.ernanilima.auth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18n {

    public static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    public static final String MESSAGE_SUCCESS_INSERT = "message.success.insert";
    public static final String MESSAGE_SUCCESS_UPDATE = "message.success.update";
    public static final String MESSAGE_SUCCESS_DELETE = "message.success.delete";

    public static String getFieldName(String s) {
        return getMessage(s);
    }

    public static String getMessage(String s) {
        Objects.requireNonNull(s);
        return ResourceBundle.getBundle("message", I18n.DEFAULT_LOCALE)
                .getString(s.toLowerCase());
    }
}
