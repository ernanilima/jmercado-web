package br.com.ernanilima.auth.builder;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.domain.enums.RoleENUM;
import br.com.ernanilima.auth.dto.UserBasicDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserBuilder {

    public static User create() {
        return User.builder()
                .id(UUID.fromString("9e38510d-41fa-438b-8676-d143c4bf9296"))
                .name("Nome do usuario")
                .email("email.user@email.com")
                .password("password")
                .company(CompanyBuilder.create())
                .roles(Set.of(RoleENUM.SUPPORT.getRole()))
                .build();
    }

    public static UserDTO createDTO() {
        return UserDTO.builder()
                .id(UUID.fromString("9e38510d-41fa-438b-8676-d143c4bf9296"))
                .name("Nome do usuario")
                .email("email.user@email.com")
                .password("password")
                .company(CompanyBuilder.createDTO())
                .roles(Set.of(RoleENUM.SUPPORT.getRole()))
                .build();
    }

    public static UserDTO createDTOToInsert() {
        return UserDTO.builder()
                .name("Nome do usuario")
                .email("email.user.insert@email.com")
                .password("123123")
                .roles(Set.of(RoleENUM.ADMINISTRATOR.getRole()))
                .build();
    }

    public static UserBasicDTO createBasicDTO() {
        return UserBasicDTO.builder()
                .name("Nome do usuario")
                .email("email.user@email.com")
                .password("password")
                .build();
    }

    public static UserVerification createVerification() {
        return UserVerification.builder()
                .id(UUID.fromString("53493526-82a8-402b-8967-5d7c35c4ea9e"))
                .user(UserBuilder.create())
                .securityLink("http://site.com/AbCd")
                .securityCode("Z1x2W3")
                .minutesExpiration(30)
                .checked(Boolean.FALSE)
                .build();
    }

    public static UserVerificationDTO createVerificationDTO() {
        return UserVerificationDTO.builder()
                .id(UUID.fromString("53493526-82a8-402b-8967-5d7c35c4ea9e"))
                .user(UserBuilder.createDTO())
                .securityLink("http://site.com/AbCd")
                .securityCode("Z1x2W3")
                .minutesExpiration(30)
                .checked(Boolean.FALSE)
                .build();
    }

    public static UserSpringSecurity createUserSpringSecurity() {
        User user = UserBuilder.create();
        return UserSpringSecurity.builder()
                .key(user.getId())
                .companyEin(user.getCompany().getEin())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .build();
    }
}
