package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.UserVerification;
import br.com.ernanilima.auth.dto.UserVerificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserVerificationConverter implements DTOConverter<UserVerification, UserVerificationDTO> {

    private final UserConverter userConverter;

    @Override
    public UserVerification toEntity(UserVerificationDTO dto) {
        return UserVerification.builder()
                .id(dto.getId())
                .user(userConverter.toEntity(dto.getUser()))
                .securityLink(dto.getSecurityLink())
                .securityCode(dto.getSecurityCode())
                .minutesExpiration(dto.getMinutesExpiration())
                .checked(dto.isChecked())
                .build();
    }

    @Override
    public UserVerificationDTO toDTO(UserVerification entity) {
        return UserVerificationDTO.builder()
                .id(entity.getId())
                .user(userConverter.toDTO(entity.getUser()))
                .securityLink(entity.getSecurityLink())
                .securityCode(entity.getSecurityCode())
                .minutesExpiration(entity.getMinutesExpiration())
                .checked(entity.isChecked())
                .build();
    }
}
