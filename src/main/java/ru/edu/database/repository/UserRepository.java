package ru.edu.database.repository;

import java.util.List;
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

}
