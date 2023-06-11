package ru.edu.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.edu.ApplicationRunner;

@EnableJpaAuditing // кто и когда создал/обновил
// для таблицы с историей
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
@Configuration
public class AuditConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return () -> Optional.ofNullable(authentication)
      .map(a -> (UserDetails) a.getPrincipal())
      .map(UserDetails::getUsername);
  }

}
