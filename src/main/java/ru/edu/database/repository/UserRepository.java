package ru.edu.database.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.edu.database.pool.ConnectionPool;

@Component
@RequiredArgsConstructor
public class UserRepository {

  @Qualifier("pool2")
  private final ConnectionPool connectionPool;

}
