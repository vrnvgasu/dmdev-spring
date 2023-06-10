package ru.edu.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.edu.validation.imp.UserInfoValidator;

@Constraint(validatedBy = UserInfoValidator.class) // реализация
@Target(ElementType.TYPE) // над классом
@Retention(RetentionPolicy.RUNTIME) // доступ во время выполнения
public @interface UserInfo {

  String message() default "Firstname or lastname should be filled in";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

}
