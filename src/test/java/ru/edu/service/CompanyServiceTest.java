package ru.edu.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CrudRepository;
import ru.edu.dto.CompanyReadDto;
import ru.edu.listener.entity.EntityEvent;

// для обработки @Mock и @InjectMocks
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

  private static final Integer COMPANY_ID = 1;

  @Mock
  private CrudRepository<Integer, Company> companyRepository;
  @Mock
  private UserService userService;
  @Mock
  private ApplicationEventPublisher eventPublisher;

  @InjectMocks
  private CompanyService companyService;

  @Test
  void findById() {
    Mockito.doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.emptyMap())))
      .when(companyRepository).findById(COMPANY_ID);

    var actualResult = companyService.findById(COMPANY_ID);

    Assertions.assertTrue(actualResult.isPresent());

    var expectedResult = new CompanyReadDto(COMPANY_ID);
    actualResult.ifPresent(actual -> assertEquals(expectedResult, actualResult.get()));

    // проверяем, что вызывался event
    Mockito.verify(eventPublisher).publishEvent(Mockito.any(EntityEvent.class));
    // проверяем, что больше ничего не дергали
    Mockito.verifyNoMoreInteractions(eventPublisher, userService);
  }

}