package ru.edu.http.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// делаем только для rest
@ControllerAdvice(basePackages = "ru.edu.http.rest")
// ResponseEntityExceptionHandler уже включает в себя большой handleException
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

}
