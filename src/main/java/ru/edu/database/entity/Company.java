package ru.edu.database.entity;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
@NamedQuery(
  name = "Company.findByName",
  query = "select c from Company c where lower(c.name) = lower(:name2)"
)
public class Company implements BaseEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @Builder.Default // чтобы при билдере ставилось = new HashMap<>()
  @ElementCollection
  @CollectionTable(name = "company_locales",
    joinColumns = @JoinColumn(name = "company_id"))
  @MapKeyColumn(name = "lang") // ключ
  @Column(name = "description") // значение
  // задаем значение по умолчанию, чтобы не ловить null pointer
  private Map<String, String> locales = new HashMap<>();

}
