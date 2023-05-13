package ru.edu.service;

import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CrudRepository;
import ru.edu.dto.CompanyReadDto;
import ru.edu.listener.entity.AccessType;
import ru.edu.listener.entity.EntityEvent;

@Service
public class CompanyService {

  private final CrudRepository<Integer, Company> companyRepository;
  private final UserService userService;

  // интерфейс, который публикует события (ивенты)
  private final ApplicationEventPublisher eventPublisher;

  public CompanyService(CrudRepository<Integer, Company> companyRepository, UserService userService,
    ApplicationEventPublisher eventPublisher) {
    this.companyRepository = companyRepository;
    this.userService = userService;
    this.eventPublisher = eventPublisher;
  }

  public Optional<CompanyReadDto> findById(Integer id) {
    return companyRepository.findById(id)
      .map(entity -> {
        // вызываем свой кастомный ивент EntityEvent
        eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));

        return new CompanyReadDto(entity.id());
      });
  }

}
