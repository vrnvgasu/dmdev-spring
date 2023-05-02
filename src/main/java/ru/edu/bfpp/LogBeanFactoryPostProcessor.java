package ru.edu.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
    throws BeansException {
    System.out.println("LogBeanFactoryPostProcessor");
  }

  @Override
  public int getOrder() {
    // чем больше, тем раньше вызывается
    return Ordered.HIGHEST_PRECEDENCE;
  }

}
