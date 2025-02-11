package com.lojavirtual.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API da Loja Virtual")
                        .version("1.0.0")
                        .description("Documentação da API da Loja Virtual")
                        .contact(new Contact()
                                .name("Emanuel")
                                .email("emanuelconte.dev@gmail.com")
                                .url("https://github.com/emanuelconte/Virtual-Store-Api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}