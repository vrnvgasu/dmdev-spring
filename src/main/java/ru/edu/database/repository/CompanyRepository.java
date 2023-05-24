package ru.edu.database.repository;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import ru.edu.database.entity.Company;

@org.springframework.stereotype.Repository
public interface CompanyRepository extends Repository<Company, Integer> {

  Optional<Company> findById(Integer id);

  void delete(Company id);

}
