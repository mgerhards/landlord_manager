package de.propadmin.rentalmanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.stream.Collectors;

@Configuration
public class GlobalRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    public GlobalRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Dynamically expose ids for all entities
        config.exposeIdsFor(
            entityManager.getMetamodel()
                         .getEntities()
                         .stream()
                         .map(EntityType::getJavaType)
                         .toArray(Class[]::new)
        );
    }
}
