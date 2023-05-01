package ru.edu.database.repository;

import java.util.Optional;
import javax.annotation.PostConstruct;
import ru.edu.database.bpp.Auditing;
import ru.edu.database.bpp.InjectBean;
import ru.edu.database.bpp.Transaction;
import ru.edu.database.entity.Company;
import ru.edu.database.pool.ConnectionPool;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

  @InjectBean // сделаем свою реализацию пост процесса при создании бинов
  private ConnectionPool connectionPool;

  @Override
  public Optional<Company> findById(Integer id) {
    System.out.println("findById method ...");
    return Optional.of(new Company(id));
  }

  @Override
  public void delete(Integer id) {
    System.out.println("delete method ...");
  }

  @PostConstruct
  private void init() {
    System.out.println("init CompanyRepository ...");
  }

}
