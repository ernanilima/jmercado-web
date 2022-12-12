package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.CompanyDTO;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CompanyDTO findById(UUID id);

    CompanyDTO findByEin(String ein);

    List<CompanyDTO> findAll();

    void insert(CompanyDTO dto);

    void update(UUID id, CompanyDTO dto);

}
