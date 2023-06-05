package ru.edu.database.repository;

import java.util.List;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;
import ru.edu.dto.PersonalInfo;
import ru.edu.dto.UserFilter;

public interface FilterUserRepository  {

  List<User> findAllByUserFilter(UserFilter filter);

  List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

}
