package ru.edu.mapper;

import static java.util.function.Predicate.not;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import ru.edu.database.entity.Company;
import ru.edu.database.entity.User;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.dto.UserCreateEditDto;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

  private final CompanyRepository companyRepository;

  @Override
  public User map(UserCreateEditDto fromObject, User toObject) {
    copy(fromObject, toObject);
    return toObject;
  }

  @Override
  public User map(UserCreateEditDto object) {
    User user = new User();
    copy(object, user);

    return user;
  }

  private void copy(UserCreateEditDto object, User user) {
    user.setUsername(object.getUsername());
    user.setFirstname(object.getFirstname());
    user.setLastname(object.getLastname());
    user.setBirthDate(object.getBirthDate());
    user.setRole(object.getRole());
    user.setCompany(getCompany(object.getCompanyId()));

    // сохраним название картинки, только если она передана и не пустая
    Optional.ofNullable(object.getImage())
      .filter(not(MultipartFile::isEmpty))
      .ifPresent(image -> user.setImage(image.getOriginalFilename()));
  }

  public Company getCompany(Integer companyId) {
    return Optional.ofNullable(companyId)
      .flatMap(companyRepository::findById)
      .orElse(null);
  }
}
