package ru.edu.database.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.edu.database.bpp.Auditing;
import ru.edu.database.bpp.Transaction;
import ru.edu.database.entity.Company;
import ru.edu.database.pool.ConnectionPool;

@Slf4j
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
    log.info("findById method ...");
    return Optional.of(new Company(id, null, Collections.emptyMap()));
  }

  @Override
  public void delete(Integer id) {
    log.info("delete method ...");
  }

  @PostConstruct
  private void init() {
    log.warn("init CompanyRepository ...");
  }

}
