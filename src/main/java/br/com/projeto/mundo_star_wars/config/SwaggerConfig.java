package br.com.projeto.mundo_star_wars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("API Mundo Star Wars")
            .version("v1")
            .description("API de cadastro e autenticação de usuários do Mundo Star Wars")
        );
  }
}
