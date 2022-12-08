package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompanyConverter implements DTOConverter<Company, CompanyDTO> {

    private final AddressConverter addressConverter;

    @Override
    public Company toEntity(CompanyDTO dto) {
        return Company.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .tradingName(dto.getTradingName())
                .ein(dto.getEin())
                .email(dto.getEmail())
                .address(addressConverter.toEntity(dto.getAddress()))
                .build();
    }
}
