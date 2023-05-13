package ru.edu.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CrudRepository;
import ru.edu.dto.CompanyReadDto;

@Service
public class CompanyService {

  private final CrudRepository<Integer, Company> companyRepository;
  private final UserService userService;

  public CompanyService(CrudRepository<Integer, Company> companyRepository, UserService userService) {
    this.companyRepository = companyRepository;
    this.userService = userService;
  }

  public Optional<CompanyReadDto> findById(Integer id) {
    return companyRepository.findById(id)
      .map(entity -> new CompanyReadDto(entity.id()));
  }

}
