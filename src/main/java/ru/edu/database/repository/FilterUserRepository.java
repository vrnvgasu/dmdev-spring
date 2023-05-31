package ru.edu.database.repository;

import java.util.List;
import ru.edu.database.entity.User;
import ru.edu.dto.UserFilter;

public interface FilterUserRepository  {

  List<User> findAllByUserFilter(UserFilter filter);

}
