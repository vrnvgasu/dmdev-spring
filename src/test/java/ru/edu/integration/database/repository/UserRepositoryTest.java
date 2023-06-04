package ru.edu.integration.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;
import ru.edu.database.entity.Role;
import ru.edu.database.entity.User;
import ru.edu.database.repository.UserRepository;
import ru.edu.dto.PersonalInfo;
import ru.edu.dto.PersonalInfo2;
import ru.edu.dto.UserFilter;
import ru.edu.integration.annotation.IT;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

  private final UserRepository userRepository;

  @Test
  void checkAuditing() {
    var ivan = userRepository.findById(1L).get();
    ivan.setBirthDate(ivan.getBirthDate().plusYears(1));
    userRepository.flush();
    System.out.println();
  }

  @Test
  void checkCustomImplementation() {
    UserFilter filter = new UserFilter(
      null, "ov", LocalDate.now()
    );
    List<User> users = userRepository.findAllByUserFilter(filter);
    System.out.println(users);
  }

  @Test
  void checkProjections() {
//    List<PersonalInfo> users = userRepository.findAllByCompanyId(1, PersonalInfo.class);
    List<PersonalInfo2> users = userRepository.findAllByCompanyId(1);
    Assertions.assertThat(users).hasSize(2);
    System.out.println(users);
  }

  @Test
  void checkPageable() {
    TypedSort<User> sortBy = Sort.sort(User.class);
    Sort sort = sortBy.by(User::getFirstname).descending().and(sortBy.by(User::getLastname));

    // вторая страница (отсчет от 0), размер 2, сортировка выше
    Pageable pageable = PageRequest.of(1, 2, sort);

    Page<User> userSlice = userRepository.findAllBy(pageable);
    Assertions.assertThat(userSlice).hasSize(2);
    userSlice.forEach(user -> System.out.println(user.getId()));

    // перебираем страницы
    while (userSlice.hasNext()) {
      // userSlice уже содержит next page (PageRequest.of(2 и тд, 2, sort))
      userSlice = userRepository.findAllBy(userSlice.nextPageable());
      userSlice.forEach(user -> System.out.println(user.getCompany().getName()));
    }
  }

  @Test
  void checkSort() {
    // хардкорно пишем параметры для сортировки
//    Sort sort = Sort.by("firstname").descending().and(Sort.by("lastname"));

    // пишем типобезопасно
    TypedSort<User> sortBy = Sort.sort(User.class);
    Sort sort = sortBy.by(User::getFirstname).descending().and(sortBy.by(User::getLastname));

    var users = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sort);
    System.out.println(users);
  }

  @Test
  void checkFirstTop() {
    Optional<User> topUser = userRepository.findTopByOrderByIdDesc();
    assertTrue(topUser.isPresent());
    topUser.ifPresent(user -> assertEquals(5L, user.getId()));

    var users = userRepository.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now());
    Assertions.assertThat(users).hasSize(3);
  }

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