package ru.edu.database.bpp;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionBeanPostProcessor implements BeanPostProcessor {

  // сохраняем, что приходит в Before (т.к. в After можем подменить на прокси)
  private final Map<String, Class<?>> transactionBeans = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().isAnnotationPresent(Transaction.class)) {
      transactionBeans.put(beanName, bean.getClass());
    }

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Class<?> beanClass = transactionBeans.get(beanName);

    if (beanClass != null) {
      // что-то похожее делали в курсе hibernate
      // создавали прокси, чтобы можно было работать в разных потоках
      return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
        (proxy, method, args) -> {
          System.out.println("Open transaction ...");
          try {
            // вызываем сам метод (работа с БД)
            return method.invoke(bean, args);
          } catch (Exception e) {
            System.out.println("Rollback transaction");
            throw e;
          } finally {
            System.out.println("Close transaction ...");
          }
        }
      );
    }

    return bean;
  }

}
