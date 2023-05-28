package ru.edu.integration.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;
import ru.edu.database.repository.UserRepository;
import ru.edu.integration.annotation.IT;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

  private final UserRepository userRepository;

  @Test
  void checkUpdate() {
    User ivan = userRepository.getById(1L);
    assertSame(Role.ADMIN, ivan.getRole());

    var resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
    assertEquals(2, resultCount);

//    String name = ivan.getCompany().getName();

    User theSameIvan = userRepository.getById(1L);
    assertSame(Role.USER, theSameIvan.getRole());
  }

  @Test
  void checkQueries() {
    List<User> users = userRepository.findAllBy("a", "ov");
    System.out.println(users);
  }

}