package ru.edu.database.repository;

import static ru.edu.database.entity.QUser.user;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;
import ru.edu.database.querydsl.QPredicates;
import ru.edu.dto.PersonalInfo;
import ru.edu.dto.UserFilter;

@RequiredArgsConstructor
// важно, что реализация совпадают с названием репозитория + Impl
public class FilterUserRepositoryImpl implements FilterUserRepository {

  private static final String FIND_BY_COMPANY_AND_ROLE = """
        select firstname, lastname, birth_date
        from users
        where company_id = ?
        and role = ?
    """;

  private final EntityManager entityManager;

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
    return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE, (rs, rowNum) -> new PersonalInfo(
      rs.getString("firstname"),
      rs.getString("lastname"),
      rs.getDate("birth_date").toLocalDate()
    ), companyId, role.name());
  }

  @Override
  // динамическая имплементация
  public List<User> findAllByUserFilter(UserFilter filter) {
    Predicate predicate = QPredicates.builder()
      .add(filter.firstname(), user.firstname::containsIgnoreCase)
      .add(filter.lastname(), user.lastname::containsIgnoreCase)
      .add(filter.birthDate(), user.birthDate::before)
      .build();

    return new JPAQuery<User>(entityManager)
      .select(user)
      .from(user)
      .where(predicate)
      .fetch();
  }

}
