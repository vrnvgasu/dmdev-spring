package ru.edu.validation.imp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.validation.UserInfo;

// все валидаторы в спринге по умолчанию являются бинами
@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

  private final CompanyRepository companyRepository;

  @Override
  public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
    return StringUtils.hasText(value.getFirstname()) || StringUtils.hasText(value.getLastname());
  }

}
