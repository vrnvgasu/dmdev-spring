package ru.edu.http.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.database.repository.CompanyRepository;

@Controller // @Controller - это компонент
public class GreetingController {

  // HandlerMethodArgumentResolver позволяет делать DI многих объектов
  // в методы контроллера. Можно вставлять request, response и любой бин
  public ModelAndView hello(ModelAndView modelAndView,
    HttpServletRequest request, CompanyRepository companyRepository ) {
    modelAndView.setViewName("greeting/hello");
    return modelAndView;
  }

  public ModelAndView bye(ModelAndView modelAndView) {
    modelAndView.setViewName("greeting/bye");
    return modelAndView;
  }

}
