package ru.edu.database.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.edu.database.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

  // будет строить запрос из названия метода по регулярке с помощью реализации PartTreeJpaQuery
  Optional<Company> findByName(@Param("name2") String name);

  // Containing - аналог LIKE %fragment%
  // IgnoreCase - просто приводит в where все к upper
  List<Company> findAllByNameContainingIgnoreCase(String fragment);

}
