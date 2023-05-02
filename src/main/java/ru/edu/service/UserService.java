package ru.edu.service;

import org.springframework.stereotype.Service;
import ru.edu.database.entity.Company;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.CrudRepository;
import ru.edu.database.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final CrudRepository<Integer, Company> companyRepository;

  public UserService(
    UserRepository userRepository,
    // посколько CrudRepository будет несколько, то тут спринг ищет бин по id бина "companyRepository"
    CrudRepository<Integer, Company> companyRepository) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }

}
