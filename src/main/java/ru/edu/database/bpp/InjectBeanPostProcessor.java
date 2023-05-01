package ru.edu.database.bpp;

import java.util.Arrays;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

// своя костыльная реализация Autowired
public class InjectBeanPostProcessor implements
  // для реализации метод пост процесса
  // для начала реализуем postProcessBeforeInitialization
  BeanPostProcessor,
  // инжектит в объект контекст спринга
  ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    // берем для каждого бина все его поля
    Arrays.stream(bean.getClass().getDeclaredFields())
      // оставляем только поля с аннотацией InjectBean
      .filter(field -> field.isAnnotationPresent(InjectBean.class))
      .forEach(field -> {
        // берем из контейнера объект класса этого поля
        Object beanToInject = applicationContext.getBean(field.getType());
        // делаем поле доступным и помещаем в него созданный объект
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, bean, beanToInject);
      });

    return bean;
  }

  @Override
  // DI контекса спринга из ApplicationContextAware
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
