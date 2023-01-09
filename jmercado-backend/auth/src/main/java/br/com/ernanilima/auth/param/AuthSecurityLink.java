package br.com.ernanilima.auth.param;

import br.com.ernanilima.auth.service.validation.Get;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthSecurityLink {

    @NotEmpty(message = "{empty.field}", groups = {Get.class})
    @Length(min = 255, max = 255, message = "{length.security.field}", groups = {Get.class})
    private String securityLink;

}
