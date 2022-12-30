package br.com.ernanilima.auth.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
public class ErrorMultipleFields extends StandardError {

    @Getter
    private final List<FieldErrorMessage> errors = new ArrayList<>();

    void addError(String fieldName, String message) {
        errors.add(new FieldErrorMessage(fieldName, message));
    }

    @Getter
    @AllArgsConstructor
    private static class FieldErrorMessage implements Serializable {
        private String fieldName;
        private String message;
    }
}
