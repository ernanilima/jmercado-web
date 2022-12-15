package br.com.ernanilima.auth.param;

import br.com.ernanilima.auth.service.validation.Get;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import static br.com.ernanilima.auth.service.validation.AuthRegex.EMAIL_REGEX;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthEmail {

    @NotEmpty(message = "{empty.field}", groups = {Get.class})
    @Email(regexp = EMAIL_REGEX, message = "{invalid.email}", groups = {Get.class})
    private String email;

}
