package ru.edu.database.repository;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import ru.edu.database.bpp.Auditing;
import ru.edu.database.bpp.InjectBean;
import ru.edu.database.bpp.Transaction;
import ru.edu.database.entity.Company;
import ru.edu.database.pool.ConnectionPool;

@Transaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

//  @Resource(name = "pool1") // java EE спецификация
//  @Autowired // Аннотация спринга
//  @Qualifier("pool1")
  private ConnectionPool pool1;

  @Autowired
  private List<ConnectionPool> connectionPools;

  // подтянет данные из src/main/resources/application.properties
  @Value("${db.pool.size}")
  private Integer poolSize;

  @Autowired
  public void setPool1(ConnectionPool pool1) {
    this.pool1 = pool1;
  }

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
