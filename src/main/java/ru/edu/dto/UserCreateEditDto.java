package ru.edu.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.edu.database.entity.Role;
import ru.edu.validation.UserInfo;
import ru.edu.validation.group.CreateClass;
import ru.edu.validation.group.UpdateClass;

@Value
@FieldNameConstants
@UserInfo(groups = UpdateClass.class)
public class UserCreateEditDto {

  @Email
  String username;

  //  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate birthDate;

  @Size(min = 3, max = 64)
  String firstname;

  String lastname;

  Role role;

  Integer companyId;

}
