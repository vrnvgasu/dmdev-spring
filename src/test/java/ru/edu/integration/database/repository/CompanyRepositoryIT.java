package ru.edu.integration.database.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionTemplate;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.integration.IntegrationTestBase;

@RequiredArgsConstructor
//@Rollback // используется по умолчанию в тестах для каждого метода
//@Commit // вместо @Rollback делает коммит после каждого теста
class CompanyRepositoryIT extends IntegrationTestBase {

  private static final Integer APPLE_ID = 10;

  // можем делать DI, т.к. в @IT подключен @SpringBootTest
  private final EntityManager entityManager;

  private final TransactionTemplate transactionTemplate;

  private final CompanyRepository companyRepository;

  @Test
  void checkFindByQueries() {
    /*
    select
        company0_.id as id1_1_,
        company0_.name as name2_1_
    from company company0_
    where
        company0_.name=?
     */
    companyRepository.findByName("Google");

    /*
    select
        company0_.id as id1_1_,
        company0_.name as name2_1_
    from company company0_
    where
        upper(company0_.name) like upper(?) escape ?
     */
    companyRepository.findAllByNameContainingIgnoreCase("a");
  }

  @Test
  @Disabled
  void delete() {
    var maybeCompany = companyRepository.findById(APPLE_ID);
    assertTrue(maybeCompany.isPresent());
    maybeCompany.ifPresent(companyRepository::delete);

    // delete не произойдет, пока не произойдет commit или не вызовем flush явно
    entityManager.flush();
    assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
  }

  @Test
  void findById() {
    // сможет выполнить код с БД внутри бина TransactionTemplate,
    // даже если нет аннотации @Transactional (засунули ее в @IT)
    transactionTemplate.executeWithoutResult(transaction -> {
      var company = entityManager.find(Company.class, 1);

      assertNotNull(company);
      assertThat(company.getLocales())
        .hasSize(2);

      System.out.println(company);
    });
  }

  @Test
  void save() {
    var company = Company.builder()
      .name("Apple")
      .locales(Map.of(
        "ru", "описание",
        "en", "description"
      ))
      .build();
    entityManager.persist(company);
    assertNotNull(company.getId());
  }

}