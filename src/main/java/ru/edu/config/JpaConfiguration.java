package ru.edu.config;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import ru.edu.config.condition.JpaCondition;

@Conditional(JpaCondition.class) // условия, включать или не включать в контекст
@Configuration
public class JpaConfiguration {

  @PostConstruct
  void init() {
    System.out.println("JpaConfiguration is enabled");
  }

}
