package ru.edu.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import ru.edu.config.condition.JpaCondition;

@Slf4j // создаст константу log с методами для логирования
@Conditional(JpaCondition.class) // условия, включать или не включать в контекст
@Configuration
public class JpaConfiguration {

//  @Bean
//  // @ConfigurationProperties - фича спрингбута, а не обычного спринга
//  // db - свойства какого ключа маппим из application.yml
//  @ConfigurationProperties(prefix = "db")
//  public DatabaseProperties databaseProperties() {
//    return new DatabaseProperties();
//  }

  @PostConstruct
  void init() {
    log.info("JpaConfiguration is enabled");
  }

}
