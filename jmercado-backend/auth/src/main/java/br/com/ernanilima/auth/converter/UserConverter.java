package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverter implements DTOConverter<User, UserDTO> {

    private final CompanyConverter companyConverter;

    @Override
    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .company(companyConverter.toEntity(dto.getCompany()))
                .build();
    }

    @Override
    public UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .company(companyConverter.toDTO(entity.getCompany()))
                .build();
    }
}
