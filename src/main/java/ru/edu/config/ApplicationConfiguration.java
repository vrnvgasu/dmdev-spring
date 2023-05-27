package ru.edu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.UserRepository;
import ru.web.config.WebConfiguration;

// можно подключить xml и читать бины также оттуда
//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration(/*proxyBeanMethods = false*/)
public class ApplicationConfiguration {

  // название бина: название метода или название в аннотации @Bean
  @Bean("pool2") // бины считывает ConfigurationClassBeanDefinitionReader
  @Scope(BeanDefinition.SCOPE_SINGLETON)
  public ConnectionPool pool2(
    @Value("${db.username}") String username,
    @Value("${db.pool.size}") Integer pollSize
  ) {
    return new ConnectionPool(username, pollSize);
  }

  @Bean // id бина - "pool3" - из названия метода
  public ConnectionPool pool3() {
    return new ConnectionPool("test-pool", 25);
  }

}
