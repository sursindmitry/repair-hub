package com.sursindmitry.repairhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .servers(
            List.of(
                new Server().url("http://localhost:8080")
            )
        )
        .info(
            new Info().title("Repair Hub API")
                .description("Get heavy-duty equipment repair services and more. "
                    + "Register to keep track of your order history and stay up to date with news.")
                .contact(
                    new Contact().url("https://github.com/sursindmitry/repair-hub")
                        .email("sursidnmitryi@gmail.com"))
        );
  }
}
