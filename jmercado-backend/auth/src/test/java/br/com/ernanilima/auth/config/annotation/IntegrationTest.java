package br.com.ernanilima.auth.config.annotation;

import br.com.ernanilima.auth.AuthApplication;
import br.com.ernanilima.auth.config.DatabaseExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Documented
@Rollback
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, DatabaseExtension.class})
@ActiveProfiles({"test"})
@SpringBootTest(classes = {AuthApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface IntegrationTest {

}
