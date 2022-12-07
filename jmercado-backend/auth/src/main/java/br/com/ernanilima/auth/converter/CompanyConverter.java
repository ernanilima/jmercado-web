package br.com.ernanilima.auth.converter;

import br.com.ernanilima.auth.domain.Company;
import br.com.ernanilima.auth.dto.CompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter implements DTOConverter<Company, CompanyDTO> {

    @Override
    public Company toEntity(CompanyDTO dto) {
        return Company.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .tradingName(dto.getTradingName())
                .ein(dto.getEin())
                .email(dto.getEmail())
                .build();
    }
}
