package ru.edu.service;

import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CrudRepository;
import ru.edu.dto.CompanyReadDto;
import ru.edu.listener.entity.AccessType;
import ru.edu.listener.entity.EntityEvent;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

  private final CrudRepository<Integer, Company> companyRepository;
  private final UserService userService;

  // интерфейс, который публикует события (ивенты)
  private final ApplicationEventPublisher eventPublisher;

  @Transactional // @Transactional над методом имеет приоритет над классов
  public Optional<CompanyReadDto> findById(@NonNull Integer id) {
    return companyRepository.findById(id)
      .map(entity -> {
        // вызываем свой кастомный ивент EntityEvent
        eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));

        return new CompanyReadDto(entity.getId());
      });
  }

}
