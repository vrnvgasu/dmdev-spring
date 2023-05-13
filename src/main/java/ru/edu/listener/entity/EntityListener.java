package ru.edu.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component // листенер должен быть компонентом
public class EntityListener {

  // можем добавлять условие в condition, когда сработает листенер
  @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
  @Order(10) // можно листнеры упорядочевать
  public void acceptEntityRead(EntityEvent entityEvent) {
  }

}
