package ru.edu.database.pool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pool1")
@RequiredArgsConstructor
@Getter
public class ConnectionPool {

  @Value("${db.username}")
  private final String username;

  @Value("${db.pool.size}")
  private final Integer poolSize;

  @PostConstruct
  private void init() {
    System.out.println("Init connection pool");
  }

  // отработает при явной закрытии контекста через close()
  @PreDestroy
  private void destroy() {
    System.out.println("Clean connection pool");
  }

}
