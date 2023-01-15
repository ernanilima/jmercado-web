package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.config.annotation.IntegrationTest;
import br.com.ernanilima.auth.core.StandardUsers;
import br.com.ernanilima.auth.domain.Company;
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

@IntegrationTest
@Sql(scripts = {
        "classpath:/db/address.sql",
        "classpath:/db/company.sql",
        "classpath:/db/contact.sql"
})
class CompanyRepositoryTestIT {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("Deve retornar a empresa buscada pelo cnpj")
    void findByEin_Must_Return_The_Company_Sought_By_EIN() {
        String ein = "00000000000191";
        Optional<Company> company = companyRepository.findByEin(ein);

        assertTrue(company.isPresent());

        assertThat(company.get().getId(), is(UUID.fromString("7c0aa10d-9d62-4993-a086-2fcb8516aa52")));
        assertThat(company.get().getCompanyName(), is("Empersa1 LTDA"));
        assertThat(company.get().getTradingName(), is("Empresa1 Muito Forte"));
        assertThat(company.get().getEin(), is(ein));
        assertThat(company.get().getAddress().getId(), is(UUID.fromString("f266ae81-dc50-4ff4-8b30-fbb3626d0423")));
        assertThat(company.get().getContacts().stream().toList().get(0).getId(), is(UUID.fromString("730ef6a2-761a-44e9-9b92-a4ea4dc3366e")));
        assertThat(company.get().getCreatedBy(), is(StandardUsers.UNAUTHENTICATED_UUID));
        assertThat(company.get().getCreatedDate(), is(LocalDateTime.of(2023, 1, 1, 10, 20, 30)));
    }

    @Test
    @DisplayName("Deve retornar uma empresa vazia ao buscar pelo cnpj que nao existe")
    void findByEin_Must_Return_An_Empty_Company_When_Searched_For_By_The_EIN_That_Does_Not_Exist() {
        Optional<Company> company = companyRepository.findByEin("00000000000272");

        assertFalse(company.isPresent());
    }
}