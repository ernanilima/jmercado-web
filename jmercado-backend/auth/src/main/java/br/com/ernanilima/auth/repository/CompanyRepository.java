package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByEin(String ein);

}
