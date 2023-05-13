package ru.edu.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component // листенер должен быть компонентом
public class EntityListener {

  @EventListener
  public void acceptEntity(EntityEvent entityEvent) {
    System.out.println("Entity: " + entityEvent);
  }

}
