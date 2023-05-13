package ru.edu.database.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.edu.database.pool.ConnectionPool;

@Component
public class UserRepository {

  private final ConnectionPool connectionPool;

  public UserRepository(@Qualifier("pool2") ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

}
