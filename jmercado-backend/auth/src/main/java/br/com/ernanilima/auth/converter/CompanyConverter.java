package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CompanyConverter implements DTOConverter<Company, CompanyDTO> {

    private final AddressConverter addressConverter;
    private final ContactConverter contactConverter;

    @Override
    public Company toEntity(CompanyDTO dto) {
        return Company.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .tradingName(dto.getTradingName())
                .ein(dto.getEin())
                .address(addressConverter.toEntity(dto.getAddress()))
                .contacts(dto.getContacts().stream().map(contactConverter::toEntity).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public CompanyDTO toDTO(Company entity) {
        return CompanyDTO.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .tradingName(entity.getTradingName())
                .ein(entity.getEin())
                .address(addressConverter.toDTO(entity.getAddress()))
                .contacts(entity.getContacts().stream().map(contactConverter::toDTO).collect(Collectors.toSet()))
                .build();
    }
}
