package ru.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.SpringProperties;
import ru.edu.config.DatabaseProperties;
import ru.edu.database.pool.ConnectionPool;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

  public static void main(String[] args) {
    var context = SpringApplication.run(ApplicationRunner.class, args);
    System.out.println(context.getBeanDefinitionCount());
    var pool = context.getBean("pool1", ConnectionPool.class);
    System.out.println(pool.getPoolSize());
    System.out.println(pool.getUsername());

    System.out.println(context.getBean(DatabaseProperties.class));

    System.out.println(SpringProperties.getProperty("test.message"));
  }

}
