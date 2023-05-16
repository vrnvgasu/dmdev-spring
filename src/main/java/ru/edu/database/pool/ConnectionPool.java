package ru.edu.database.pool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
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
    log.info("Init connection pool");
  }

  // отработает при явной закрытии контекста через close()
  @PreDestroy
  private void destroy() {
    log.info("Clean connection pool");
  }

}
