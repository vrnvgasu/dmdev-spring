package ru.edu.database.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;
import ru.edu.dto.PersonalInfo;
import ru.edu.dto.PersonalInfo2;

public interface UserRepository extends JpaRepository<User, Long> {

  // like с % - это фишка спринга.
  // HQL (обрабатывает SimpleJpaQuery) из коробки это не поддерживает
  @Query("""
    select u from User u
    where u.firstname like %:firstName%
    and u.lastname like %:lastName%
    """)
  List<User> findAllBy(String firstName, String lastName);

  // обычно нативные запросы (обрабатывает NativeJpaQuery) используют для проекций
  @Query(value = """
    SELECT u.* 
    FROM users u 
    WHERE u.username = :username
    """,
    nativeQuery = true)
  List<User> findByUsername(String username);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("""
    update User u
    set u.role = :role
    where u.id in (:ids)
    """)
  int updateRole(Role role, Long... ids);

  // первого пользователя с максимальным ID
  Optional<User> findTopByOrderByIdDesc();

  // 3 пользователя с фильтрацией по birthDate и DESC
  List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
  List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

//  Slice<User> findAllBy(Pageable pageable);

  // Page делает дополнительный запрос на count(*)
  // можем его переопределить в countQuery
//  @Query(value = "select u from User u",
//    countQuery = "select count(distinct u .firstname) from User u")
//  Page<User> findAllBy(Pageable pageable);


  // User.company - название именного графа, описанного в сущности User
//  @EntityGraph("User.company")
  // attributePaths = {"company"} - перечисляем связи, которые надо подтянуть
  @EntityGraph(attributePaths = {"company", "company.locales"})
  @Query(value = "select u from User u",
    countQuery = "select count(distinct u .firstname) from User u")
  Page<User> findAllBy(Pageable pageable);

  // используем конкретный тип для проекции к запросу
//  List<PersonalInfo> findAllByCompanyId(Integer companyId);

  // подставляем любые проекции
//  <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

  @Query(nativeQuery = true,
    value = """
      SELECT firstname, 
      lastname, 
      birth_date as birthDate 
      FROM users WHERE company_id = :companyId
      """)
  List<PersonalInfo2> findAllByCompanyId(Integer companyId);

}
