package ru.edu.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import ru.edu.config.DatabaseProperties;
import ru.edu.dto.CompanyReadDto;
import ru.edu.integration.IntegrationTestBase;
import ru.edu.integration.annotation.IT;
import ru.edu.service.CompanyService;

//@ActiveProfiles("test")
//@SpringBootTest
@RequiredArgsConstructor // DI через конструктор
// можно заменить на spring.test.constructor.autowire.mode=all
//@TestConstructor(autowireMode = AutowireMode.ALL)
public class CompanyServiceIT extends IntegrationTestBase {

  private static final Integer COMPANY_ID = 1;

  private final CompanyService companyService;
  private final DatabaseProperties databaseProperties;

  @Test
  void findById() {
    var actualResult = companyService.findById(COMPANY_ID);

    Assertions.assertTrue(actualResult.isPresent());

    var expectedResult = new CompanyReadDto(COMPANY_ID, null);
    actualResult.ifPresent(actual -> assertEquals(expectedResult, actualResult.get()));

    System.out.println(databaseProperties);
  }

}
