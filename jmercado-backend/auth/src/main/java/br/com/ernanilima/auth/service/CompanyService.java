package br.com.ernanilima.auth.service;

import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.service.message.Message;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CompanyDTO findById(UUID id);

    CompanyDTO findByEin(String ein);

    List<CompanyDTO> findAll();

    Message insert(CompanyDTO dto);

    Message update(UUID id, CompanyDTO dto);

    Message delete(UUID id);

}
