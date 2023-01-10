package br.com.ernanilima.auth.repository;

import br.com.ernanilima.auth.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, UUID> {

    @Query(value = """
            SELECT userverification FROM UserVerification userverification
                WHERE security_link = :securityLink
                AND EXTRACT(EPOCH FROM (LOCALTIMESTAMP() - created_date))/60 <= minutes_expiration
                AND checked = FALSE
            """)
    Optional<UserVerification> findBySecurityLink(@Param("securityLink") String securityLink);

//    @Query(value = """
//            SELECT CASE WHEN COUNT(userverification) > 0 THEN true ELSE false END FROM UserVerification userverification
//              WHERE security_link = :securityLink
//                AND EXTRACT(EPOCH FROM (LOCALTIMESTAMP() - created_date))/60 <= minutes_expiration
//                AND checked = FALSE
//            """)
//    Boolean isOnTimeAndUncheckedBySecurityLink(@Param("securityLink") String securityLink);
}
