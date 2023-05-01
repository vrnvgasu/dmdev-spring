package ru.edu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.CrudRepository;

public class ApplicationRunner {

  public static void main(String[] args) {
    try(var context = new ClassPathXmlApplicationContext("application.xml")) {
      var connectionPool = context.getBean("p2", ConnectionPool.class);
      System.out.println(context.getBean("driver", String.class));

      var companyRepository = context.getBean("companyRepository", CrudRepository.class);
      System.out.println(companyRepository.findById(1));
    }
  }

}
