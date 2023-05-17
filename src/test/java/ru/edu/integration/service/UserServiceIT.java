package ru.edu.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.integration.annotation.IT;
import ru.edu.service.UserService;

// в @IT подставили @SpringBootTest(classes = TestApplicationRunner.class)
@IT
@RequiredArgsConstructor
// вручную пересоздаем контекст после каждого метода
// (можно нам классом и методом использовать)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT {

  private final UserService userService;

  // будет spy из TestApplicationRunner
  private final ConnectionPool pool1;

  @Test
  void test() {
    System.out.println();
  }

  @Test
  void test2() {
    System.out.println();
  }

}
