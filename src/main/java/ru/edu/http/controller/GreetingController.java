package ru.edu.http.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.database.repository.CompanyRepository;

@Controller // @Controller - это компонент
@RequestMapping("/api/v1") // общий префикс к uri
public class GreetingController {

  // HandlerMethodArgumentResolver позволяет делать DI многих объектов
  // в методы контроллера. Можно вставлять request, response и любой бин
//  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @GetMapping("/hello/{id}")
  public ModelAndView hello(ModelAndView modelAndView,
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
  public ModelAndView bye(ModelAndView modelAndView) {
    modelAndView.setViewName("greeting/bye");
    return modelAndView;
  }

}
