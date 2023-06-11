package ru.edu.database.entity;

import org.springframework.security.core.GrantedAuthority;

// GrantedAuthority - для авторизации
public enum Role implements GrantedAuthority {
  USER, ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
