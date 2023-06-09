package ru.edu.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.dto.CompanyReadDto;
import ru.edu.listener.entity.AccessType;
import ru.edu.listener.entity.EntityEvent;
import ru.edu.mapper.CompanyReadMapper;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;

  private final ApplicationEventPublisher eventPublisher;

  private final CompanyReadMapper companyReadMapper;

  public Optional<CompanyReadDto> findById(Integer id) {
    return companyRepository.findById(id)
      .map(entity -> {
        eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
        return companyReadMapper.map(entity);
      });
  }

  public List<CompanyReadDto> findAll() {
    return companyRepository.findAll().stream()
      .map(companyReadMapper::map)
      .toList();
  }

}
