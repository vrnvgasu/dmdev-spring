package ru.edu;

import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.UserRepository;
import ru.edu.ioc.Container;
import ru.edu.service.UserService;

public class ApplicationRunner {

  public static void main(String[] args) {
    Container container = new Container();

    var connectionPool = container.get(ConnectionPool.class);
    var userRepository = container.get(UserRepository.class);
    var companyRepository = container.get(CompanyRepository.class);
    UserService userService = container.get(UserService.class);

//    var connectionPool = new ConnectionPool();
//    var userRepository = new UserRepository(connectionPool);
//    var companyRepository = new CompanyRepository(connectionPool);
//    UserService userService = new UserService(userRepository, companyRepository);
  }

}
