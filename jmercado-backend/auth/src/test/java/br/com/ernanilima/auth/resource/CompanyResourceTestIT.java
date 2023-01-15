package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.AuthTestIT;
import br.com.ernanilima.auth.config.annotation.IntegrationTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Sql(scripts = {
        "classpath:/db/address.sql",
        "classpath:/db/company.sql",
        "classpath:/db/contact.sql",
        "classpath:/db/user.sql",
        "classpath:/db/userrole.sql"
})
class CompanyResourceTestIT extends AuthTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    private String token;

    @BeforeEach
    void setup() {
        token = super.getUserToken();
    }

    @Test
    void findByEin() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/cnpj/{ein}", "00000000000191")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", token))

                // deve retornar o Status 200
                .andExpect(status().isOk());
    }
}