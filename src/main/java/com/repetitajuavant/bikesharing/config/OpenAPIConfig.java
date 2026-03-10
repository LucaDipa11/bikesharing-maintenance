package com.repetitajuavant.bikesharing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration - Configurazione di Swagger/OpenAPI per la documentazione API
 * 
 * La documentazione sarà disponibile a:
 * - Swagger UI: http://localhost:8080/swagger-ui.html
 * - OpenAPI JSON: http://localhost:8080/v3/api-docs
 * - OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI bikeSharingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bike Sharing Management API")
                        .description("API REST per gestire il sistema di ticketing e manutenzione del bike sharing del Comune di Repetita Juvant")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Repetita Juvant Municipality")
                                .email("support@repetitajuvant.it")
                                .url("https://repetitajuvant.it")));
    }
}
