package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.AuthTestIT;
import br.com.ernanilima.auth.config.annotation.IntegrationTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("Deve retornar uma empresa buscada pelo id")
    void findById_Must_Return_A_Company_Searched_For_By_Id() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/{id}", "7c0aa10d-9d62-4993-a086-2fcb8516aa52")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar a empresa buscada pelo cnpj")
    void findByEin_Must_Return_The_Company_Sought_By_EIN() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/cnpj/{ein}", "00000000000191")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk());
    }
}