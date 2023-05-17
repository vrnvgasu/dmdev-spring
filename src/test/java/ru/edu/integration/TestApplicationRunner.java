package ru.edu.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.edu.database.pool.ConnectionPool;

// считывает @SpringBootApplication из ApplicationRunner
@TestConfiguration
public class TestApplicationRunner {

  // name - id бина
  @SpyBean(name = "pool1")
//  @MockBean(name = "pool1")
  private ConnectionPool pool1;

}
