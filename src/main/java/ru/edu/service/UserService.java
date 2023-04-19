package ru.edu.service;

import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.UserRepository;

public class UserService {

  private final UserRepository userRepository;

  private final CompanyRepository companyRepository;

  public UserService(UserRepository userRepository, CompanyRepository companyRepository) {
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
  }

}
