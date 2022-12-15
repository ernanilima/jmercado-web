package br.com.ernanilima.auth.param;

import br.com.ernanilima.auth.service.validation.Get;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

import static br.com.ernanilima.auth.service.validation.AuthRegex.UUID_REGEX;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthUUID {

    @NotEmpty(message = "{empty.field}", groups = {Get.class})
    @Pattern(message = "{invalid.uuid}", regexp = UUID_REGEX, groups = {Get.class})
    private String id;

    public UUID getId() {
        return UUID.fromString(this.id);
    }
}
