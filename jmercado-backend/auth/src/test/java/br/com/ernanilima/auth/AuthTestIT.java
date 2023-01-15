package br.com.ernanilima.auth;

import br.com.ernanilima.auth.builder.AuthenticationBuilder;
import br.com.ernanilima.auth.config.annotation.IntegrationTest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@IntegrationTest
public abstract class AuthTestIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    protected String getUserToken() {
        try {
            String userOk = gson.toJson(AuthenticationBuilder.getUserForLogin());
            return this.mockMvc
                    .perform(MockMvcRequestBuilders
                            .post("/auth/login")
                            .contentType(APPLICATION_JSON)
                            .content(userOk))
                    .andReturn().getResponse().getHeader("Authorization");
        } catch (Exception e) {
            throw new RuntimeException("Token nao gerado");
        }
    }
}
