package br.com.ernanilima.auth.resource;

import br.com.ernanilima.auth.AuthTestIT;
import br.com.ernanilima.auth.builder.CompanyBuilder;
import br.com.ernanilima.auth.config.annotation.IntegrationTest;
import br.com.ernanilima.auth.dto.AddressDTO;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.ContactDTO;
import br.com.ernanilima.auth.dto.UserBasicDTO;
import br.com.ernanilima.auth.service.exception.DataIntegrityException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("Deve retornar uma empresa buscada pelo id (com token)")
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
    @DisplayName("Deve retornar uma mensagem de erro por nao enviar o token para buscar uma empresa por id")
    void findById_Must_Return_An_Error_Message_For_Not_Sending_The_Token_To_Search_For_A_Company_By_Id() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/{id}", "7c0aa10d-9d62-4993-a086-2fcb8516aa52")
                        .contentType(APPLICATION_JSON))

                // deve retornar o Status 401
                .andExpect(status().isUnauthorized())
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Erro de autenticação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Token inválido")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao nao localizar a empresa buscada pelo id (com token)")
    void findById_Must_Return_An_Error_Message_When_Not_Finding_The_Company_Sought_By_Id() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/{id}", "0d16f4a9-e3ba-499c-bd40-b28668387f51")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 404
                .andExpect(status().isNotFound())
                // deve retornar uma excecao 'ObjectNotFoundException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Não encontrado")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Empresa não encontrado(a)")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao pesquisar uma empresa com o id invalido (com token)")
    void findById_Must_Return_An_Error_Message_When_Searching_For_A_Company_With_Invalid_Id() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/{id}", "UUID_INVALIDO")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 422
                .andExpect(status().isUnprocessableEntity())
                // deve retornar uma excecao 'BindException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BindException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Validação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Quantidade de erro(s): 1")))
                // deve retornar a quantiadde de erro(s)
                .andExpect(jsonPath("$.errors.*", hasSize(1)))

                // deve retornar o nome o campo e a mensagem de erro
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'ID')].message", contains("ID inválido")));
    }

    @Test
    @DisplayName("Deve retornar todas as empresas cadastradas (com token)")
    void findAll_Must_Return_All_Registered_Companies() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro por nao enviar o token para buscar todas as empresas")
    void findAll_Must_Return_An_Error_Message_For_Not_Sending_The_Token_To_Fetch_All_Companies() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa")
                        .contentType(APPLICATION_JSON))

                // deve retornar o Status 401
                .andExpect(status().isUnauthorized())
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Erro de autenticação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Token inválido")));
    }

    @Test
    @DisplayName("Deve retornar sucesso ao inserir uma empresa com o usuario principal (sem passar token)")
    void insert_Must_Return_Success_When_Inserting_A_Company() throws Exception {
        String dtoJson = gson.toJson(CompanyBuilder.createDTOToInsert());
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 201
                .andExpect(status().isCreated())
                // deve retornar o id criaddo para a empresa
                .andExpect(jsonPath("$.id").exists())
                // deve retornar a mensagem com o resultado
                .andExpect(jsonPath("$.message", is("Registro inserido com sucesso")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao tentar inserir uma empresa com o CNPJ repetido (sem passar token)")
    void insert_Must_Return_An_Error_Message_When_Trying_To_Insert_A_Company_With_The_Repeated_EIN() throws Exception {
        CompanyDTO dtoToInsert = CompanyBuilder.createDTOToInsert().toBuilder()
                .ein("00000000000191").build();
        String dtoJson = gson.toJson(dtoToInsert);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 400
                .andExpect(status().isBadRequest())
                // deve retornar uma excecao 'DataIntegrityException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Integridade de dados")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("O valor do campo `CNPJ` já está sendo usado")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao inserir uma empresa sem nenhum dado (sem passar token)")
    void insert_Must_Return_An_Error_Message_When_Entering_A_Company_Without_Any_Data() throws Exception {
        String dtoJson = gson.toJson(CompanyDTO.builder().build());
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 422
                .andExpect(status().isUnprocessableEntity())
                // deve retornar uma excecao 'MethodArgumentNotValidException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Validação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Quantidade de erro(s): 6")))
                // deve retornar a quantiadde de erro(s)
                .andExpect(jsonPath("$.errors.*", hasSize(6)))

                // deve retornar o nome o campo e a mensagem de erro
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Razão social')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Nome fantasia')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'CNPJ')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Endereço')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Contatos')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Usuário')].message", contains("Preenchimento obrigatório")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao inserir uma empresa sem os dados para endereco (sem passar token)")
    void insert_Must_Return_An_Error_Message_When_Entering_A_Company_Without_Address_Data() throws Exception {
        CompanyDTO dtoToInsert = CompanyBuilder.createDTOToInsert().toBuilder()
                .address(AddressDTO.builder().build()).build();
        String dtoJson = gson.toJson(dtoToInsert);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 422
                .andExpect(status().isUnprocessableEntity())
                // deve retornar uma excecao 'MethodArgumentNotValidException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Validação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Quantidade de erro(s): 10")))
                // deve retornar a quantiadde de erro(s)
                .andExpect(jsonPath("$.errors.*", hasSize(10)))

                // deve retornar o nome o campo e a mensagem de erro
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'CEP')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'País')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Região')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Estado')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Cidade')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Bairro')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Rua')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Número')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'IBGE')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'DDD')].message", contains("Preenchimento obrigatório")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao inserir uma empresa sem os dados para contato (sem passar token)")
    void insert_Must_Return_An_Error_Message_When_Entering_A_Company_Without_Contact_Data() throws Exception {
        CompanyDTO dtoToInsert = CompanyBuilder.createDTOToInsert().toBuilder()
                .contacts(Set.of(ContactDTO.builder().build())).build();
        String dtoJson = gson.toJson(dtoToInsert);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 422
                .andExpect(status().isUnprocessableEntity())
                // deve retornar uma excecao 'MethodArgumentNotValidException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Validação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Quantidade de erro(s): 1")))
                // deve retornar a quantiadde de erro(s)
                .andExpect(jsonPath("$.errors.*", hasSize(1)))

                // deve retornar o nome o campo e a mensagem de erro
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'E-mail')].message", contains("Preenchimento obrigatório")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao inserir uma empresa sem os dados para usuario (sem passar token)")
    void insert_Must_Return_An_Error_Message_When_Entering_A_Company_Without_User_Data() throws Exception {
        CompanyDTO dtoToInsert = CompanyBuilder.createDTOToInsert().toBuilder()
                .user(UserBasicDTO.builder().build()).build();
        String dtoJson = gson.toJson(dtoToInsert);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/empresa")
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson))

                // deve retornar o Status 422
                .andExpect(status().isUnprocessableEntity())
                // deve retornar uma excecao 'MethodArgumentNotValidException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Validação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Quantidade de erro(s): 3")))
                // deve retornar a quantiadde de erro(s)
                .andExpect(jsonPath("$.errors.*", hasSize(3)))

                // deve retornar o nome o campo e a mensagem de erro
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Nome')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'E-mail')].message", contains("Preenchimento obrigatório")))
                .andExpect(jsonPath("$.errors[?(@.fieldName == 'Senha')].message", contains("Preenchimento obrigatório")));
    }

    @Test
    @DisplayName("Deve retornar sucesso ao atualizar uma empresa e depois obter os dados atualizados (com token)")
    void update_Must_Return_Success_When_Updating_A_Company() throws Exception {
        CompanyDTO dtoToUpdate = CompanyBuilder.createDTOToUpdate();
        String dtoJson = gson.toJson(dtoToUpdate);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/empresa/{id}", dtoToUpdate.getId())
                        .contentType(APPLICATION_JSON)
                        .content(dtoJson)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk())
                // deve retornar o id da empresa atualizada
                .andExpect(jsonPath("$.id", is(dtoToUpdate.getId().toString())))
                // deve retornar a mensagem com o resultado
                .andExpect(jsonPath("$.message", is("Registro atualizado com sucesso")));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/{id}", dtoToUpdate.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk())
                // deve retornar os dados que foram atualizados
                .andExpect(jsonPath("$.id", is(dtoToUpdate.getId().toString())))
                .andExpect(jsonPath("$.companyName", is(dtoToUpdate.getCompanyName())))
                .andExpect(jsonPath("$.tradingName", is(dtoToUpdate.getTradingName())))
                .andExpect(jsonPath("$.ein", is(dtoToUpdate.getEin())))
                .andExpect(jsonPath("$.address.id", is(dtoToUpdate.getAddress().getId().toString())))
                .andExpect(jsonPath("$.address.zipCode", is(dtoToUpdate.getAddress().getZipCode())))
                .andExpect(jsonPath("$.address.country", is(dtoToUpdate.getAddress().getCountry())))
                .andExpect(jsonPath("$.address.region", is(dtoToUpdate.getAddress().getRegion())))
                .andExpect(jsonPath("$.address.state", is(dtoToUpdate.getAddress().getState())))
                .andExpect(jsonPath("$.address.city", is(dtoToUpdate.getAddress().getCity())))
                .andExpect(jsonPath("$.address.district", is(dtoToUpdate.getAddress().getDistrict())))
                .andExpect(jsonPath("$.address.street", is(dtoToUpdate.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.number", is(dtoToUpdate.getAddress().getNumber())))
                .andExpect(jsonPath("$.address.complement", is(dtoToUpdate.getAddress().getComplement())))
                .andExpect(jsonPath("$.address.code", is(dtoToUpdate.getAddress().getCode())))
                .andExpect(jsonPath("$.address.areaCode", is(dtoToUpdate.getAddress().getAreaCode())))
                .andExpect(jsonPath("$.contacts[0].id", is(dtoToUpdate.getContacts().stream().toList().get(0).getId().toString())))
                .andExpect(jsonPath("$.contacts[0].email", is(dtoToUpdate.getContacts().stream().toList().get(0).getEmail())))
                .andExpect(jsonPath("$.contacts[0].telephone", is(dtoToUpdate.getContacts().stream().toList().get(0).getTelephone())))
                .andExpect(jsonPath("$.contacts[0].cellPhone", is(dtoToUpdate.getContacts().stream().toList().get(0).getCellPhone())))
                .andExpect(jsonPath("$.contacts[0].whatsappCellPhone", is(dtoToUpdate.getContacts().stream().toList().get(0).isWhatsappCellPhone())));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro por nao enviar o token para atualizar uma empresas")
    void update_Must_Return_An_Error_Message_For_Not_Sending_The_Token_To_Update_A_Company() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/empresa/{id}", "db2c1162-998b-4204-afc5-2003a8eb8060")
                        .contentType(APPLICATION_JSON))

                // deve retornar o Status 401
                .andExpect(status().isUnauthorized())
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Erro de autenticação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Token inválido")));
    }

    @Test
    @DisplayName("Deve retornar sucesso ao excluir uma empresa sem vinculacoes (com token)")
    void delete_Must_Return_Success_When_Deleting_An_Unlinked_Company() throws Exception {
        String uuid = "db2c1162-998b-4204-afc5-2003a8eb8060";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/empresa/{id}", uuid)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 200
                .andExpect(status().isOk())
                // deve retornar o id passado
                .andExpect(jsonPath("$.id", is(uuid)))
                // deve retornar a mensagem com o resultado
                .andExpect(jsonPath("$.message", is("Registro excluido com sucesso")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro por nao enviar o token para excluir uma empresas")
    void delete_Must_Return_An_Error_Message_For_Not_Sending_The_Token_To_Delete_A_Company() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/empresa/{id}", "db2c1162-998b-4204-afc5-2003a8eb8060")
                        .contentType(APPLICATION_JSON))

                // deve retornar o Status 401
                .andExpect(status().isUnauthorized())
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Erro de autenticação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Token inválido")));
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro ao tentar excluir uma empresa com vinculacoes (com token)")
    void delete_Must_Return_An_Error_Message_When_Trying_To_Delete_A_Company_With_Link() throws Exception {
        String uuid = "7c0aa10d-9d62-4993-a086-2fcb8516aa52";
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/empresa/{id}", uuid)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", super.getUserToken()))

                // deve retornar o Status 400
                .andExpect(status().isBadRequest())
                // deve retornar uma excecao 'DataIntegrityException'
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityException))
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Integridade de dados")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Empresa possui vinculações, impossível remover")));
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

    @Test
    @DisplayName("Deve retornar uma mensagem de erro por nao enviar o token para buscar uma empresa pelo cnpj")
    void findByEin_Must_Return_An_Error_Message_For_Not_Sending_The_Token_To_Search_For_A_Company_By_EIN() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/empresa/cnpj/{ein}", "00000000000191")
                        .contentType(APPLICATION_JSON))

                // deve retornar o Status 401
                .andExpect(status().isUnauthorized())
                // deve retornar o titulo de orientacao do erro
                .andExpect(jsonPath("$.error", is("Erro de autenticação")))
                // deve retornar a mensagem de orientacao do erro
                .andExpect(jsonPath("$.message", is("Token inválido")));
    }
}