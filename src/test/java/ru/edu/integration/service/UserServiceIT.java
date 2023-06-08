package ru.edu.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.edu.database.entity.Role;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.dto.UserReadDto;
import ru.edu.integration.IntegrationTestBase;
import ru.edu.service.UserService;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

  private static final Long USER_1 = 1L;

  private static final Integer COMPANY_1 = 1;

  private final UserService userService;

  @Test
  void findAll() {
    List<UserReadDto> result = userService.findAll();
    assertThat(result).hasSize(5);
  }

  @Test
  void findById() {
    Optional<UserReadDto> maybeUser = userService.findById(USER_1);
    assertTrue(maybeUser.isPresent());
    maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
  }

  @Test
  void create() {
    UserCreateEditDto userDto = new UserCreateEditDto(
      "test@gmail.com",
      LocalDate.now(),
      "Test",
      "Test",
      Role.ADMIN,
      COMPANY_1
    );
    UserReadDto actualResult = userService.create(userDto);

    assertEquals(userDto.getUsername(), actualResult.getUsername());
    assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
    assertEquals(userDto.getFirstname(), actualResult.getFirstname());
    assertEquals(userDto.getLastname(), actualResult.getLastname());
    assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
    assertSame(userDto.getRole(), actualResult.getRole());
  }

  @Test
  void update() {
    UserCreateEditDto userDto = new UserCreateEditDto(
      "test@gmail.com",
      LocalDate.now(),
      "Test",
      "Test",
      Role.ADMIN,
      COMPANY_1
    );

    Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);

    assertTrue(actualResult.isPresent());
    actualResult.ifPresent(user -> {
      assertEquals(userDto.getUsername(), user.getUsername());
      assertEquals(userDto.getBirthDate(), user.getBirthDate());
      assertEquals(userDto.getFirstname(), user.getFirstname());
      assertEquals(userDto.getLastname(), user.getLastname());
      assertEquals(userDto.getCompanyId(), user.getCompany().id());
      assertSame(userDto.getRole(), user.getRole());
    });
  }

  @Test
  void delete() {
    assertFalse(userService.delete(-124L));
    assertTrue(userService.delete(USER_1));
  }

}