package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, UUID> {

}
