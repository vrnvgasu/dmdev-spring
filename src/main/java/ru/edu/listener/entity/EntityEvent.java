package ru.edu.listener.entity;

import java.util.EventObject;

// EventObject содержит поле
// protected transient Object source;
// т.е. сам объект, над которым вызывается событие
public class EntityEvent extends EventObject {

  private final AccessType accessType;

  public EntityEvent(Object entity, AccessType accessType) {
    super(entity);
    this.accessType = accessType;
  }

  public AccessType getAccessType() {
    return accessType;
  }

}
