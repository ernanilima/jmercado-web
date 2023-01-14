package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.config.annotation.RepositoryTest;
import br.com.ernanilima.auth.core.StandardUsers;
import br.com.ernanilima.auth.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
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
        "classpath:/db/userrole.sql"
})
class UserRepositoryTestIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve retornar o usuario pelo seu e-mail e cnpj da sua empresa")
    void findByEmailAndCompany_Ein_Must_Return_The_User_By_Your_Email_And_Your_Company_EIN() {
        int email = 0;
        int ein = 1;
        String[] data = {"email.user1@email.com", "00000000000191"};
        Optional<User> user = userRepository.findByEmailAndCompany_Ein(data[email], data[ein]);

        assertTrue(user.isPresent());

        assertThat(user.get().getId(), is(UUID.fromString("9e38510d-41fa-438b-8676-d143c4bf9296")));
        assertThat(user.get().getName(), is("Nome do usuario1"));
        assertThat(user.get().getEmail(), is(data[email]));
        assertThat(user.get().getPassword(), is("password"));
        assertThat(user.get().getCompany().getId(), is(UUID.fromString("7c0aa10d-9d62-4993-a086-2fcb8516aa52")));
        assertThat(user.get().getCompany().getEin(), is(data[ein]));
        assertThat(user.get().getRoles().stream().toList().get(0).getId(), is(9998));
        assertThat(user.get().getCreatedBy(), is(StandardUsers.UNAUTHENTICATED_UUID));
        assertThat(user.get().getCreatedDate(), is(LocalDateTime.of(2023, 1, 1, 10, 20, 30)));
    }

    @Test
    @DisplayName("Deve retornar um usuario vazio ao buscar pelo seu e-mail e cnpj de outra empresa")
    void findByEmailAndCompany_Ein_Must_Return_A_Empty_User_When_Searching_For_Your_Email_And_EIN_From_Another_Company() {
        Optional<User> user = userRepository.findByEmailAndCompany_Ein("email.user1@email.com", "00000000000272");

        assertFalse(user.isPresent());
    }
}