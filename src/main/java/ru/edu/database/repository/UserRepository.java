package ru.edu.database.repository;

import org.springframework.stereotype.Component;
import ru.edu.database.pool.ConnectionPool;

@Component
public class UserRepository {

  private final ConnectionPool connectionPool;

  public UserRepository(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

}
