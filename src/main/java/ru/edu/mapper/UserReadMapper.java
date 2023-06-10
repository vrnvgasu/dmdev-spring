package ru.edu.mapper;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.edu.database.entity.User;
import ru.edu.dto.CompanyReadDto;
import ru.edu.dto.UserReadDto;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

  private final CompanyReadMapper companyReadMapper;

  @Override
  public UserReadDto map(User object) {
    CompanyReadDto company = Optional.ofNullable(object.getCompany())
      .map(companyReadMapper::map)
      .orElse(null);
    return new UserReadDto(
      object.getId(),
      object.getUsername(),
      object.getBirthDate(),
      object.getFirstname(),
      object.getLastname(),
      object.getRole(),
      company,
      object.getImage()
    );
  }

}
