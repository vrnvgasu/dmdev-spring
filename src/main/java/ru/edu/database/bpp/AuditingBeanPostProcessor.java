package ru.edu.database.bpp;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuditingBeanPostProcessor implements BeanPostProcessor {

  // сохраняем, что приходит в Before (т.к. в After можем подменить на прокси)
  private final Map<String, Class<?>> auditBeans = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().isAnnotationPresent(Auditing.class)) {
      auditBeans.put(beanName, bean.getClass());
    }

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Class<?> beanClass = auditBeans.get(beanName);

    if (beanClass != null) {
      // что-то похожее делали в курсе hibernate
      // создавали прокси, чтобы можно было работать в разных потоках
      return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
        (proxy, method, args) -> {
          System.out.println("Audit method: " + method.getName());
          var startTime = System.nanoTime();
          try {
            // вызываем сам метод (работа с БД)
            return method.invoke(bean, args);
          } finally {
            System.out.println("Time execution: " + (System.nanoTime() - startTime));
          }
        }
      );
    }

    return bean;
  }

}
