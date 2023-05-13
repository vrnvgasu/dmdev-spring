package ru.edu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.edu.config.ApplicationConfiguration;
import ru.edu.database.pool.ConnectionPool;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.CrudRepository;
import ru.edu.database.repository.UserRepository;
import ru.edu.service.CompanyService;

public class ApplicationRunner {

  public static void main(String[] args) {
//    try(var context = new ClassPathXmlApplicationContext("application.xml")) {
//    try(var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {

    // не можем сразу передать ApplicationConfiguration в конструктор контекста,
    // т.к. тогда не сработает context.refresh()
    try(var context = new AnnotationConfigApplicationContext()) {
      context.register(ApplicationConfiguration.class);
      // установить профайл системы (можно несколько)
      context.getEnvironment().setActiveProfiles("web", "prod");
      context.refresh(); // обновить контекст, чтобы пересоздать бины с профайлами выше

      var connectionPool = context.getBean("pool1", ConnectionPool.class);
//      System.out.println(context.getBean("driver", String.class));

      var companyRepository = context.getBean("companyRepository", CrudRepository.class);
      System.out.println(companyRepository.findById(1));

      var companyService = context.getBean(CompanyService.class);
      companyService.findById(1);
    }
  }

}
