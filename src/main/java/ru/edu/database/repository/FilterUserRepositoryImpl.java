package ru.edu.database.repository;

import static ru.edu.database.entity.QUser.user;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import ru.edu.database.entity.User;
import ru.edu.database.querydsl.QPredicates;
import ru.edu.dto.UserFilter;

@RequiredArgsConstructor
// важно, что реализация совпадают с названием репозитория + Impl
public class FilterUserRepositoryImpl implements FilterUserRepository {

  private final EntityManager entityManager;

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
