package ru.edu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.CompanyRepository;

public class ApplicationRunner {

  public static void main(String[] args) {
    try(var context = new ClassPathXmlApplicationContext("application.xml")) {
      var connectionPool = context.getBean("p2", ConnectionPool.class);
      System.out.println(context.getBean("driver", String.class));

      CompanyRepository companyRepository = context.getBean("companyRepository", CompanyRepository.class);
      System.out.println(companyRepository);
    }
  }

}
