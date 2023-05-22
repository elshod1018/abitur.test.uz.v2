package uz.test.abitur.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.NonNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static uz.test.abitur.utils.UrlUtils.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }


    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Abitur Test Uz")
                        .description("API for applicants solving questions")
                        .version("2.0")
                        .contact(new Contact()
                                .name("Elshod Nuriddinov ")
                                .email("nuriddinovelshod2003@gmail.com")
                                .url("https://github.com/elshod1018"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .termsOfService("http://swagger.io/terms/"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Wikipedia Documentation")
                        .url("https://springshop.wiki.github.org/docs"))
                .servers(List.of(
                                new Server().url("https://abiturtestuzv2.up.railway.app").description("Production Server"),
                                new Server().url("http://localhost:8080").description("Development Server")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components((new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                ));
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch(BASE_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch(BASE_AUTH_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersOpenApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch(BASE_USERS_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi newsOpenApi() {
        return GroupedOpenApi.builder()
                .group("news")
                .pathsToMatch(BASE_NEWS_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi subjectsOpenApi() {
        return GroupedOpenApi.builder()
                .group("subjects")
                .pathsToMatch(BASE_SUBJECTS_URL + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi questionsOpenApi() {
        return GroupedOpenApi.builder()
                .group("questions")
                .pathsToMatch(BASE_QUESTIONS_URL + "/**")
                .build();
    }
}
