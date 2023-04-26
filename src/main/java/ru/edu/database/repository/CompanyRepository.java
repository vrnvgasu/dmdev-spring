package ru.edu.database.repository;

import ru.edu.database.pool.ConnectionPool;

public class CompanyRepository {

  private final ConnectionPool connectionPool;

  public CompanyRepository(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  // пример фабричного метода
  public static CompanyRepository of(ConnectionPool connectionPool) {
    return new CompanyRepository(connectionPool);
  }

}
