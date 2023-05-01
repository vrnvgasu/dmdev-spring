package ru.edu.database.bpp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // доступно в рантайме
@Target(ElementType.FIELD) // используем над свойствами
public @interface InjectBean {

}
