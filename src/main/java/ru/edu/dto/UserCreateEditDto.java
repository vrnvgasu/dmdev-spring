package ru.edu.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.edu.database.entity.Role;

@Value
@FieldNameConstants
public class UserCreateEditDto {

  @Email
  String username;

  //  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate birthDate;

  @NotNull
  @Size(min = 3, max = 64)
  String firstname;

  @NotNull
  String lastname;

  Role role;

  Integer companyId;

}
