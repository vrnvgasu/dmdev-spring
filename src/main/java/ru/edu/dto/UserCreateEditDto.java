package ru.edu.dto;

import lombok.Value;

import java.time.LocalDate;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import ru.edu.database.entity.Role;

@Value
@FieldNameConstants
public class UserCreateEditDto {
  String username;
//  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate birthDate;
  String firstname;
  String lastname;
  Role role;
  Integer companyId;
}
