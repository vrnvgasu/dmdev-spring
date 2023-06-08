package ru.edu.http.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.database.entity.Role;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.dto.UserReadDto;

@Controller // @Controller - это компонент
@RequestMapping("/api/v1") // общий префикс к uri
@SessionAttributes({"user"})
public class GreetingController {

  @ModelAttribute("roles")
  public List<Role> roles() {
    return Arrays.asList(Role.values());
  }

  @GetMapping("/hello")
  public String hello(Model modelAndView, HttpServletRequest request,
    @ModelAttribute("userReadDto") UserReadDto userReadDto) {
    modelAndView.addAttribute("user", new UserReadDto(1L, "Ivan"));
    return "greeting/hello";
  }

  // HandlerMethodArgumentResolver позволяет делать DI многих объектов
  // в методы контроллера. Можно вставлять request, response и любой бин
//  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @GetMapping("/hello/{id}")
  public ModelAndView hello2(ModelAndView modelAndView,
    HttpServletRequest request, @RequestParam(value = "age", required = false) Integer age,
    @RequestHeader("accept") String accept,
    @CookieValue("JSESSIONID") String jsessionId,
    @PathVariable("id") Integer id) {
    String ageParamValue = request.getParameter("age");
    String acceptHeader = request.getHeader("accept");

    Cookie[] cookies = request.getCookies();

    modelAndView.setViewName("greeting/hello");
    return modelAndView;
  }

//  @RequestMapping(value = "/bye", method = RequestMethod.GET)
  @GetMapping("/bye")
  public String bye(ModelAndView modelAndView,
    @SessionAttribute("user") UserReadDto user) {
    return "greeting/bye"; // вместо modelAndView.setViewName("greeting/bye");
  }

}
