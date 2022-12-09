package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverter implements DTOConverter<User, UserDTO> {

    @Override
    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
