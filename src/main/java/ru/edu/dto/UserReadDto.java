package ru.edu.dto;

import lombok.Value;

// record не подходит, т.к. делаем для jsp
// а там вызываются неявно геттеры (в рекордах у них другой формат)
@Value
public class UserReadDto {
  Long id;
  String username;
}
