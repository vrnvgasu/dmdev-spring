package ru.edu.http.rest;

import javax.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.edu.dto.PageResponse;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.dto.UserFilter;
import ru.edu.dto.UserReadDto;
import ru.edu.service.CompanyService;
import ru.edu.service.UserService;
import ru.edu.validation.group.CreateClass;
import ru.edu.validation.group.UpdateClass;

//@ResponseBody // отключает MVC
//@Controller
@RestController // заменяет @Controller + @Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

  private final CompanyService companyService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
    Page<UserReadDto> page = userService.findAll(filter, pageable);

    return PageResponse.of(page);
  }

  @GetMapping("/{id}")
  public UserReadDto findById(@PathVariable("id") Long id) {
    return userService.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserReadDto create(@Validated({Default.class, CreateClass.class}) @RequestBody UserCreateEditDto user) {
    return userService.create(user);
  }

  @PutMapping("/{id}")
  public UserReadDto update(@PathVariable("id") Long id,
    @RequestBody @Validated({Default.class, UpdateClass.class}) UserCreateEditDto user) {
    return userService.update(id, user)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT) // 204
  public void delete(@PathVariable("id") Long id) {
    if (!userService.delete(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

}
