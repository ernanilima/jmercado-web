package br.com.ernanilima.auth.config.annotation;

import br.com.ernanilima.auth.AuthApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Documented
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
@SpringBootTest(classes = {AuthApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface IntegrationTest {

}
