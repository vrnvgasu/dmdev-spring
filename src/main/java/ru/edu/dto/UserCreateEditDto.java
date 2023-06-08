package ru.edu.dto;

import lombok.Value;

import java.time.LocalDate;
import ru.edu.database.entity.Role;

@Value
public class UserCreateEditDto {
  String username;
  LocalDate birthDate;
  String firstname;
  String lastname;
  Role role;
  Integer companyId;
}
