package ru.edu.dto;

import lombok.Value;

import java.time.LocalDate;
import lombok.experimental.FieldNameConstants;
import ru.edu.database.entity.Role;

@Value
@FieldNameConstants
public class UserCreateEditDto {
  String username;
  LocalDate birthDate;
  String firstname;
  String lastname;
  Role role;
  Integer companyId;
}
