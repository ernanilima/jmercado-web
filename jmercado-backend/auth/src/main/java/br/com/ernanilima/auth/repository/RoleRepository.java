package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
