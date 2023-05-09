package uz.test.abitur;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.test.abitur.config.security.SessionUser;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@EnableAsync
@OpenAPIDefinition
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final SessionUser sessionUser;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(sessionUser.id());
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
