package ru.edu.http.handler;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice // делаем глобальным для всех контроллерв
public class ControllerExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

  @ExceptionHandler(Exception.class)
  // т.к. это метод контроллера, то в него можно делать также DI
  public String handleException(Exception e, HttpServletRequest request) {
    log.error("Failed to return response", e);
    return "error/error500";
  }

}
