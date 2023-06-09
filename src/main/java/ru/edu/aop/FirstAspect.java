package ru.edu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Aspect
@Slf4j
@Component // аспект в спринге должен быть бином
@Order(1)
public class FirstAspect {

  // проверить, что этот объект реализует интерфейс Repository
  // this - проверяет proxy
  // target - сам класс
  @Pointcut("this(org.springframework.data.repository.Repository)")
//  @Pointcut("target(org.springframework.data.repository.Repository)")
  public void isRepositoryLayer() {
  }

  // @annotation - проверяет аннотацию у метода
//  @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
  // добавили условия Pointcut выше
  @Pointcut("ru.edu.aop.CommonPointcuts.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
  public void hasGetMapping() {
  }

  // args - проверяем параметры в методе
  // * - один любой параметр
  // .. - любое кол-во любых параметров
  // проверяем, что в методе первый параметр Model. А дальше неважно сколько всего параметров
  @Pointcut("ru.edu.aop.CommonPointcuts.isControllerLayer() && args(org.springframework.ui.Model,..)")
  // проверяем, что в методе первый параметр Model. А всего ровно 3 параметра
//  @Pointcut("args(org.springframework.ui.Model,*,*)")
  public void hasModelParam() {
  }

  // @args - проверяет наличие аннотации над типом класса параметра в методе
  // внимание! @Validated - это не то. Это не над типом класса параметра
  // ищем методы, где первый параметр содержит аннотацию UserInfo
  @Pointcut("ru.edu.aop.CommonPointcuts.isControllerLayer() && @args(ru.edu.validation.UserInfo,..)")
  public void hasUserInfoParamAnnotation() {
  }

  // bean - ищет название бина для объекта (не стандарт AspectJ)
  // все бины с названием *Service
  @Pointcut("bean(*Service)")
  public void isServiceLayerBean() {
  }

  /*
  ? - может и не быть
      execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
   */
//  @Pointcut("execution(public * findById(*))")
  @Pointcut("execution(public * ru.edu.service.*Service.findById(*))")
  public void anyFindByIdServiceMethod() {
  }

//  @Before("anyFindByIdServiceMethod()")
//  // JoinPoint - точка внедрения
//  public void addLogging(JoinPoint joinPoint) {
//    log.info("invoked findById method");
//  }

  @Before(value = "anyFindByIdServiceMethod() " +
    "&& args(id) " +    // знаем, что 1 аргумент. Внедрили его в DI метода с типом Long
    "&& target(service) " +
    "&& this(serviceProxy)" +
    "&& @within(transactional)",
    // argNames - чтобы IDE для старой версии java не ругалось на внедрение названий переменных из аннотации
    argNames = "joinPoint,id,service,serviceProxy,transactional")
  // JoinPoint всегда идет первым параметром
  public void addLogging(JoinPoint joinPoint,
    Long id,
    Object service,
    Object serviceProxy,
    Transactional transactional) {
    log.info("invoked findById method in class {}, with id {}", service, id);
  }

  @AfterReturning(value = "anyFindByIdServiceMethod() " +
    "&& target(service)",
    returning = "result") // returning = "result" - возвращаемое значение
  public void addLoggingAfterReturning(Object result, Object service) {
    log.info("after returning - invoked findById method in class {}, result {}", service, result);
  }

  @AfterThrowing(value = "anyFindByIdServiceMethod() " +
    "&& target(service)",
    throwing = "ex") // доступ к ошибке
  public void addLoggingAfterThrowing(Throwable ex, Object service) {
    log.info("after throwing - invoked findById method in class {}, exception {}: {}", service, ex.getClass(), ex.getMessage());
  }

  @After("anyFindByIdServiceMethod() && target(service)")
  public void addLoggingAfterFinally(Object service) {
    log.info("after (finally) - invoked findById method in class {}", service);
  }

}
