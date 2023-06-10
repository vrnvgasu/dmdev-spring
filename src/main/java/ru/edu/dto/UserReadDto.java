package ru.edu.dto;

import java.time.LocalDate;
import lombok.Value;
import ru.edu.database.entity.Role;

// record не подходит, т.к. делаем для jsp
// а там вызываются неявно геттеры (в рекордах у них другой формат)
@Value
public class UserReadDto {
  Long id;
  String username;
  LocalDate birthDate;
  String firstname;
  String lastname;
  Role role;
  CompanyReadDto company;
  String image;
}
