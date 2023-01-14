package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.config.annotation.RepositoryTest;
import br.com.ernanilima.auth.core.StandardUsers;
import br.com.ernanilima.auth.domain.UserVerification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RepositoryTest
@Sql(scripts = {
        "classpath:/db/address.sql",
        "classpath:/db/company.sql",
        "classpath:/db/contact.sql",
        "classpath:/db/user.sql",
        "classpath:/db/userrole.sql",
        "classpath:/db/userverification.sql"
})
class UserVerificationRepositoryTestIT {

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Test
    @DisplayName("Deve retornar os dados para link no prazo e que nao foi validado")
    void findBySecurityLink_Must__Return_The_Data_For_The_Link_On_Time_And_That_Has_Not_Been_Validated() {
        String securityLink = "AbCd_no_prazo_e_nao_validado";
        Optional<UserVerification> userVerification = userVerificationRepository.findBySecurityLink(securityLink);

        assertTrue(userVerification.isPresent());

        assertThat(userVerification.get().getId(), is(UUID.fromString("53493526-82a8-402b-8967-5d7c35c4ea9e")));
        assertThat(userVerification.get().getUser().getId(), is(UUID.fromString("9e38510d-41fa-438b-8676-d143c4bf9296")));
        assertThat(userVerification.get().getSecurityLink(), is(securityLink));
        assertThat(userVerification.get().getSecurityCode(), is("Z1x2W_1"));
        assertThat(userVerification.get().getMinutesExpiration(), is(30));
        assertFalse(userVerification.get().isChecked());
        assertThat(userVerification.get().getCreatedBy(), is(StandardUsers.UNAUTHENTICATED_UUID));
    }

    @Test
    @DisplayName("Deve retornar os dados vazios para link no prazo e que ja foi validado")
    void findBySecurityLink_Must_Return_Empty_Data_For_Link_On_Time_And_That_Has_Already_Been_Validated() {
        Optional<UserVerification> userVerification = userVerificationRepository.findBySecurityLink("AbCd_no_prazo_e_validado");

        assertFalse(userVerification.isPresent());
    }

    @Test
    @DisplayName("Deve retornar os dados vazios para link fora prazo e que nao foi validado")
    void findBySecurityLink_Must_Return_Empty_Data_For_An_OutOfDate_Link_That_Has_Not_Been_Validated() {
        Optional<UserVerification> userVerification = userVerificationRepository.findBySecurityLink("AbCd_fora_do_prazo_e_nao_validado");

        assertFalse(userVerification.isPresent());
    }

    @Test
    @DisplayName("Deve retornar os dados vazios para link fora prazo e que ja foi validado")
    void findBySecurityLink_Must_Return_Empty_Data_For_An_OutOfDate_Link_That_Has_Already_Been_Validated() {
        Optional<UserVerification> userVerification = userVerificationRepository.findBySecurityLink("AbCd_fora_do_prazo_e_validado");

        assertFalse(userVerification.isPresent());
    }

    @Test
    @DisplayName("Deve retornar os dados vazios para link que nao existe")
    void findBySecurityLink_Must_Return_Empty_Data_For_Link_That_Does_Not_Exist() {
        Optional<UserVerification> userVerification = userVerificationRepository.findBySecurityLink("link_nao_existe");

        assertFalse(userVerification.isPresent());
    }
}