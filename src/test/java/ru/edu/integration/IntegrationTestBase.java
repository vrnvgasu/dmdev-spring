package ru.edu.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.edu.integration.annotation.IT;

@IT
@Sql({"classpath:sql/data.sql"})
@WithMockUser(username = "testUser@t.com", password = "testPassword", authorities = {"ADMIN", "USER"})
public abstract class IntegrationTestBase {

  private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

  @BeforeAll
  static void runContainer() {
    container.start();
  }

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    // динамически заменяем свойство для ApplicationContext
    registry.add("spring.datasource.url", container::getJdbcUrl);
  }

}
