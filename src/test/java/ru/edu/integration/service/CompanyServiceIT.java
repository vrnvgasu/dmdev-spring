package ru.edu.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.edu.ApplicationRunner;
import ru.edu.database.entity.Company;
import ru.edu.dto.CompanyReadDto;
import ru.edu.listener.entity.EntityEvent;
import ru.edu.service.CompanyService;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class,
//  // ConfigDataApplicationContextInitializer подключает application.yml
//  initializers = ConfigDataApplicationContextInitializer.class)
// вместо @ExtendWith и @ContextConfiguration + работает с application.yml
@SpringBootTest
public class CompanyServiceIT {

  private static final Integer COMPANY_ID = 1;

  @Autowired // в тестах нормально делать DI через поля
  private CompanyService companyService;

  @Test
  void findById() {
    var actualResult = companyService.findById(COMPANY_ID);

    Assertions.assertTrue(actualResult.isPresent());

    var expectedResult = new CompanyReadDto(COMPANY_ID);
    actualResult.ifPresent(actual -> assertEquals(expectedResult, actualResult.get()));
  }

}
