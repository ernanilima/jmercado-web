package br.com.ernanilima.auth.lombok;

import br.com.ernanilima.auth.domain.*;
import br.com.ernanilima.auth.dto.*;
import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.dto.auth.TokenDTO;
import br.com.ernanilima.auth.dto.jkafka.SecurityVerificationDTO;
import br.com.ernanilima.auth.resource.exception.ErrorMultipleFields;
import br.com.ernanilima.auth.resource.exception.StandardError;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToStringTest {

    private UUID randomUuid;
    private Integer randomId;
    private String randomString;

    @BeforeEach
    void setup() {
        this.randomUuid = UUID.randomUUID();
        this.randomId = Integer.parseInt(RandomStringUtils.randomNumeric(5));
        this.randomString = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    void address_Builder_toString() {
        String address = Address.builder().id(randomUuid).toString();
        assertTrue(address.contains(randomUuid.toString()));
    }

    @Test
    void company_Builder_toString() {
        String company = Company.builder().id(randomUuid).toString();
        assertTrue(company.contains(randomUuid.toString()));
    }

    @Test
    void contact_Builder_toString() {
        String contact = Contact.builder().id(randomUuid).toString();
        assertTrue(contact.contains(randomUuid.toString()));
    }

    @Test
    void role_Builder_toString() {
        String role = Role.builder().id(randomId).toString();
        assertTrue(role.contains(randomId.toString()));
    }

    @Test
    void user_Builder_toString() {
        String user = User.builder().id(randomUuid).toString();
        assertTrue(user.contains(randomUuid.toString()));
    }

    @Test
    void userVerification_Builder_toString() {
        String userVerification = UserVerification.builder().id(randomUuid).toString();
        assertTrue(userVerification.contains(randomUuid.toString()));
    }

    @Test
    void loginDTO_Builder_toString() {
        String loginDTO = LoginDTO.builder().ein(randomString).toString();
        assertTrue(loginDTO.contains(randomString));
    }

    @Test
    void tokenDTO_Builder_toString() {
        String tokenDTO = TokenDTO.builder().token(randomString).toString();
        assertTrue(tokenDTO.contains(randomString));
    }

    @Test
    void securityVerificationDTO_Builder_toString() {
        String securityVerificationDTO = SecurityVerificationDTO.builder().securityLink(randomString).toString();
        assertTrue(securityVerificationDTO.contains(randomString));
    }

    @Test
    void addressDTO_Builder_toString() {
        String addressDTO = AddressDTO.builder().id(randomUuid).toString();
        assertTrue(addressDTO.contains(randomUuid.toString()));
    }

    @Test
    void companyDTO_Builder_toString() {
        String companyDTO = CompanyDTO.builder().id(randomUuid).toString();
        assertTrue(companyDTO.contains(randomUuid.toString()));
    }

    @Test
    void contactDTO_Builder_toString() {
        String contactDTO = ContactDTO.builder().id(randomUuid).toString();
        assertTrue(contactDTO.contains(randomUuid.toString()));
    }

    @Test
    void userBasicDTO_Builder_toString() {
        String userBasicDTO = UserBasicDTO.builder().name(randomString).toString();
        assertTrue(userBasicDTO.contains(randomString));
    }

    @Test
    void userDTO_Builder_toString() {
        String userDTO = UserDTO.builder().id(randomUuid).toString();
        assertTrue(userDTO.contains(randomUuid.toString()));
    }

    @Test
    void userVerificationDTO_Builder_toString() {
        String userVerificationDTO = UserVerificationDTO.builder().id(randomUuid).toString();
        assertTrue(userVerificationDTO.contains(randomUuid.toString()));
    }

    @Test
    void userSpringSecurity_Builder_toString() {
        String userSpringSecurity = UserSpringSecurity.builder().key(randomUuid).toString();
        assertTrue(userSpringSecurity.contains(randomUuid.toString()));
    }

    @Test
    void errorMultipleFields_Builder_toString() {
        String errorMultipleFields = ErrorMultipleFields.builder().message(randomString).toString();
        assertTrue(errorMultipleFields.contains(randomString));
    }

    @Test
    void standardError_Builder_toString() {
        String standardError = StandardError.builder().message(randomString).toString();
        assertTrue(standardError.contains(randomString));
    }
}
