package ru.edu.dto;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;

public interface PersonalInfo2 {

  String getFirstname();

  String getLastname();

  LocalDate getBirthDate();

  @Value("#{target.firstname + ' ' + target.lastname}")
  String getFullName();

}
