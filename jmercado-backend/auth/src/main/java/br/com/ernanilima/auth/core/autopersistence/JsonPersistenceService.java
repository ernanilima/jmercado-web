package br.com.ernanilima.auth.core.autopersistence;

import br.com.ernanilima.auth.AuthApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.AllArgsConstructor;
import org.reflections.Reflections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@AllArgsConstructor
public class JsonPersistenceService {

    private final WebApplicationContext appContext;

    @Transactional
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void persistence() {
        Repositories repositories = new Repositories(appContext);
        ObjectMapper mapper = new ObjectMapper();

        for (Class<?> classAnnotated : getReflections().getTypesAnnotatedWith(JsonPersistence.class)) {

            JpaRepository repository = (JpaRepository) repositories.getRepositoryFor(classAnnotated)
                    .filter(r -> r instanceof JpaRepository)
                    .orElseThrow(() -> new RuntimeException(
                            String.format("Repository not found for class %s", classAnnotated.getSimpleName()))
                    );

            JsonPersistence jsonPersistence = classAnnotated.getAnnotation(JsonPersistence.class);
            String jsonFileContent = readResourceAsString(jsonPersistence.jsonFile());

            try {
                List<?> objects = mapper.readValue(jsonFileContent, mapper.getTypeFactory().constructCollectionType(List.class, classAnnotated));
                repository.saveAll(objects);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private Reflections getReflections() {
        return new Reflections(AuthApplication.class.getPackage().getName());
    }

    private String readResourceAsString(String resourceName) {

        final URL resource = Resources.getResource(resourceName);

        try {
            return Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
