package ru.edu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.CrudRepository;
import ru.edu.database.repository.UserRepository;
import ru.web.config.WebConfiguration;

// можно подключить xml и читать бины также оттуда
//@ImportResource("classpath:application.xml")
@Import(WebConfiguration.class)
@Configuration(/*proxyBeanMethods = false*/)
// вместо <context:property-placeholder location="classpath:application.properties"/>
// можно file или http
@PropertySource("classpath:application.properties")
// вместо <context:component-scan
@ComponentScan(basePackages = "ru.edu", useDefaultFilters = false, includeFilters = {
  @Filter(type = FilterType.ANNOTATION, value = Component.class),
  @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
  @Filter(type = FilterType.REGEX, pattern = "ru\\.edu\\..+Repository")
})
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

  @Bean
  // пытаемся в DI найти бин с id "pool2" (по названию переменной)
  // можем искать id через @Qualifier("pool2")
  public UserRepository userRepository2(ConnectionPool pool2) {
    return new UserRepository(pool2);
  }

  @Bean
  public UserRepository userRepository3() {
    // сделали DI обращаясь к методу.
    // нормально, если бин без зависимостей, т.е. ничего не надо передавать в метод pool3()
    return new UserRepository(pool3());
  }

}
