package ru.edu.database.repository;

import static ru.edu.database.entity.QUser.user;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

  private static final String UPDATE_COMPANY_AND_ROLE = """
        update users
        set company_id = ?,
          role = ?
        where id = ?
    """;

  private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
        update users
        set company_id = :companyId,
          role = :role
        where id = :userId
    """;

  private final EntityManager entityManager;

  private final JdbcTemplate jdbcTemplate;

  private final NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
    return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE, (rs, rowNum) -> new PersonalInfo(
      rs.getString("firstname"),
      rs.getString("lastname"),
      rs.getDate("birth_date").toLocalDate()
    ), companyId, role.name());
  }

  @Override
  public void updateCompanyAndRole(List<User> users) {
    List<Object[]> args = users.stream()
      .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
      .toList();
    jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
  }

  @Override
  public void updateCompanyAndRoleNamed(List<User> users) {
    MapSqlParameterSource[] args = users.stream()
      .map(user -> Map.of(
        "companyId", user.getCompany().getId(),
        "role", user.getRole().name(),
        "userId", user.getId()
      ))
      .map(MapSqlParameterSource::new)
      .toArray(MapSqlParameterSource[]::new);
    namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
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
