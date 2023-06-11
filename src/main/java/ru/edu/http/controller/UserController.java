package ru.edu.http.controller;

import javax.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.edu.database.entity.Role;
import ru.edu.dto.PageResponse;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.dto.UserFilter;
import ru.edu.dto.UserReadDto;
import ru.edu.service.CompanyService;
import ru.edu.service.UserService;
import ru.edu.validation.group.CreateClass;
import ru.edu.validation.group.UpdateClass;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final CompanyService companyService;

  @GetMapping
  // делаем DI Pageable прямо из запроса
  public String findAll(Model model, UserFilter filter, Pageable pageable) {
    Page<UserReadDto> page = userService.findAll(filter, pageable);
    model.addAttribute("users", PageResponse.of(page));
    model.addAttribute("filter", filter);
    return "user/users";
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    return userService.findById(id)
      .map(user -> {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/user";
      })
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/registration")
  public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    model.addAttribute("companies", companyService.findAll());
    return "user/registration";
  }

  @PostMapping
//  @ResponseStatus(HttpStatus.CREATED)
  public String create(@ModelAttribute @Validated({Default.class, CreateClass.class}) UserCreateEditDto user,
    BindingResult bindingResult, // BindingResult надо ставить сразу за валидацией DTO
    RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("user", user);
      redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
      return "redirect:/users/registration";
    }

    userService.create(user);

    return "redirect:/login";
  }

  //    @PutMapping("/{id}")
  @PostMapping("/{id}/update")
  public String update(@PathVariable("id") Long id,
    @ModelAttribute @Validated({Default.class, UpdateClass.class}) UserCreateEditDto user) {
    return userService.update(id, user)
      .map(it -> "redirect:/users/{id}")
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  //    @DeleteMapping("/{id}")
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable("id") Long id) {
    if (!userService.delete(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return "redirect:/users";
  }

}
