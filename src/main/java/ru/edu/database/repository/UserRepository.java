package ru.edu.database.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;

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

  // динамическая сортировка
  List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

  List<User> findAllBy(Pageable pageable);

}
