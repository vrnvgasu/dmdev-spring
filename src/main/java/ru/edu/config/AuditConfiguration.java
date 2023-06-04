package ru.edu.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.edu.ApplicationRunner;

@EnableJpaAuditing // кто и когда создал/обновил
// для таблицы с историей
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
@Configuration
public class AuditConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    // на практике:
    // SecurityContext.getCurrentUser().getEmail()
    return () -> Optional.of("system_user");
  }

}
