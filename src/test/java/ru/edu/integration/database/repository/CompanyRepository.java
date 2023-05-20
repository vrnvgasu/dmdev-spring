package ru.edu.integration.database.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.database.entity.Company;
import ru.edu.integration.annotation.IT;

@IT
@RequiredArgsConstructor
// @Transactional дает тестам работать с БД
@Transactional // лучше из спринга
//@Rollback // используется по умолчанию в тестах для каждого метода
//@Commit // вместо @Rollback делает коммит после каждого теста
class CompanyRepository {

  // можем делать DI, т.к. в @IT подключен @SpringBootTest
  private final EntityManager entityManager;

  @Test
  void findById() {
    var company = entityManager.find(Company.class, 1);

    assertNotNull(company);
    assertThat(company.getLocales())
        .hasSize(2);

    System.out.println(company);
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