package com.gosterim360.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(buildInfo())
                .externalDocs(buildExternalDocs());
    }

    private Info buildInfo() {
        return new Info()
                .title("Gösterim360 - Movie Management API")
                .description("This API manages movie operations for the Gösterim360 ticketing system.")
                .version("1.0.0")
                .contact(buildContact())
                .license(buildLicense());
    }

    private Contact buildContact() {
        return new Contact()
                .name("Gösterim360 Dev Team")
                .url("https://gosterim360.com");
    }

    private License buildLicense() {
        return new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");
    }

    private ExternalDocumentation buildExternalDocs() {
        return new ExternalDocumentation()
                .description("Gösterim360 Project Wiki & GitHub")
                .url("https://github.com/orhanturkmenoglu/gosterim360");
    }

}
