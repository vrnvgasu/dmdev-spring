package ru.edu.integration.http.controller;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.edu.database.entity.Role;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.integration.IntegrationTestBase;

@AutoConfigureMockMvc // для имитации http запросов
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

  // объект из @AutoConfigureMockMvc
  // отправляет http запросы
  private final MockMvc mockMvc;

//  @BeforeEach
//  void init() {
//    List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
//    User testUser = new User("testUser@t.com", "testPassword", roles);
//    TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(
//      testUser,
//      testUser.getPassword(),
//      roles
//    );
//
//    SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
//    emptyContext.setAuthentication(testingAuthenticationToken);
//    SecurityContextHolder.setContext(emptyContext);
//  }

  @Test
  void findAll() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/users")
        .with(
          SecurityMockMvcRequestPostProcessors.user("testUser@t.com")
            .authorities(Role.ADMIN)
        ))
      // проверяем статус ответа
      .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
      // проверяем, какую страничку (view) отдадим
      .andExpect(MockMvcResultMatchers.view().name("user/users"))
      // проверяем, что в модели будет атрибут users
      .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
  }

  @Test
  void create() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/users")
          // добавляем к запросу обычные параметры в body
          .param(UserCreateEditDto.Fields.username, "test@test.test")
          .param(UserCreateEditDto.Fields.firstname, "firstname.test")
          .param(UserCreateEditDto.Fields.lastname, "lastname.test")
          .param(UserCreateEditDto.Fields.role, "ADMIN")
          .param(UserCreateEditDto.Fields.companyId, "1")
        // пока не можем преобразовать время в объект LocalDateTime
//      .param(UserCreateEditDto.Fields.birthDate, "2000-01-01")
      )
      .andExpectAll(
        MockMvcResultMatchers.status().is3xxRedirection(),
        // проверяем редирект на паттерн. Паттерны описаны в org.springframework.util.AntPathMatcher
        MockMvcResultMatchers.redirectedUrlPattern("/users/{\\d+}")
      );
  }

}