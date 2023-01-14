package br.com.ernanilima.auth;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@Transactional
@Rollback
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@SpringBootTest(classes = {AuthApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class AuthTestIT {

}
