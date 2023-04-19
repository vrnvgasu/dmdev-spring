package ru.edu.database.repository;

import ru.edu.database.pool.ConnectionPool;

public class UserRepository {

  private final ConnectionPool connectionPool;

  public UserRepository(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

}
