package ru.edu.mapper;

import org.springframework.stereotype.Component;
import ru.edu.database.entity.Company;
import ru.edu.dto.CompanyReadDto;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

  @Override
  public CompanyReadDto map(Company object) {
    return new CompanyReadDto(
      object.getId(),
      object.getName()
    );
  }
}
