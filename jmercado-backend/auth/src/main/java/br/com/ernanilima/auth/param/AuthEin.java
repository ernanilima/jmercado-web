package br.com.ernanilima.auth.param;

import br.com.ernanilima.auth.service.validation.Get;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthEin {

    @NotEmpty(message = "{empty.field}", groups = {Get.class})
    @Length(min = 14, max = 14, message = "{length.min.field}", groups = {Get.class})
    @CNPJ(message = "{invalid.ein}", groups = {Get.class})
    private String ein;

}
