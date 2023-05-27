package ru.edu.integration.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.edu.database.entity.User;
import ru.edu.database.repository.UserRepository;
import ru.edu.integration.annotation.IT;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

  private final UserRepository userRepository;

  @Test
  void checkQueries() {
    List<User> users = userRepository.findAllBy("a", "ov");
    System.out.println(users);
  }

}