package ru.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final CompanyRepository companyRepository;

}
