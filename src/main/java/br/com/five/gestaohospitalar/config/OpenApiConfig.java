package br.com.five.gestaohospitalar.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Gestão Hospitalar API",
    version = "1.0",
    description = "API para gestão de hospital",
    contact = @Contact(
      name = "Yuri Nascimento",
      email = "yuriidiiego@gmail.com",
      url = "https://github.com/yuriidiiego"
    ),
    license = @License(
      name = "Apache 2.0",
      url = "http://www.apache.org/licenses/LICENSE-2.0.html"
    )
  ),
  servers = {
    @Server(
      url = "http://localhost:8080",
      description = "Servidor local de desenvolvimento"
    ),
  }
)
public class OpenApiConfig {
  // @Bean
  // OpenAPI customizeOpenAPI() {
  //   final String securitySchemeName = "bearerAuth";
  //   return new OpenAPI()
  //     .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
  //     .components(
  //       new Components()
  //         .addSecuritySchemes(
  //           securitySchemeName,
  //           new SecurityScheme()
  //             .name(securitySchemeName)
  //             .type(SecurityScheme.Type.HTTP)
  //             .scheme("bearer")
  //             .bearerFormat("JWT")
  //         )
  //     );
  // }
}
