package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.CompanyDTO;

import java.util.UUID;

public interface CompanyService {

    void insert(CompanyDTO dto);

    void update(UUID id, CompanyDTO dto);

}
