package org.devalurum.transactionsapp.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi
                .builder()
                .group("Api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Transactions Account App API")
                        .description("API для демонстрации работы транзакций при переводах денег.")
                        .contact(new Contact().name("Александр").url("https://t.me/devalurum"))
                        .version("v1.0.0")
                        .license(new License().name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github source")
                        .url("https://github.com/devalurum/messenger-system"));
    }
}