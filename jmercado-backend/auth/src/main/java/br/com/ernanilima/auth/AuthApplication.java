package br.com.ernanilima.auth;

import br.com.ernanilima.auth.core.autopersistence.JsonPersistenceService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class AuthApplication implements ApplicationRunner {

    private final JsonPersistenceService jsonService;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        jsonService.persistence();
    }
}
