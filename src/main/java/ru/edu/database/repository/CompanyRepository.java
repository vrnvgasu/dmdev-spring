package ru.edu.database.repository;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edu.database.bpp.Auditing;
import ru.edu.database.bpp.Transaction;
import ru.edu.database.entity.Company;
import ru.edu.database.pool.ConnectionPool;

@Repository
@Transaction
@Auditing
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company> {

  private final ConnectionPool pool1;

  private final List<ConnectionPool> connectionPools;

  @Value("${db.pool.size}")
  private final Integer poolSize;

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
