package com.lib_for_mentor.lib_for_mentor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Lib for mentor API",
                description = "API для ментора Степана",
                version = "1.0.0",
                contact = @Contact(
                        name = "Eugene",
                        email = "eugene@mail.dev",
                        url = "https://eugene.dev"
                )
        )
)
public class OpenApiConfig {
    // Конфигурация для Swagger
}
