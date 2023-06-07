package ru.edu.http.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.database.repository.CompanyRepository;

@Controller // @Controller - это компонент
@RequestMapping("/api/v1") // общий префикс к uri
public class GreetingController {

  // HandlerMethodArgumentResolver позволяет делать DI многих объектов
  // в методы контроллера. Можно вставлять request, response и любой бин
//  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @GetMapping("/hello")
  public ModelAndView hello(ModelAndView modelAndView,
    HttpServletRequest request, CompanyRepository companyRepository ) {
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
