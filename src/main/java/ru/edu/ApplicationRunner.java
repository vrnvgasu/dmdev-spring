package ru.edu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.edu.database.pool.ConnectionPool;

public class ApplicationRunner {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    System.out.println(context.getBean("p2", ConnectionPool.class));
    System.out.println(context.getBean("driver", String.class));
  }

}
