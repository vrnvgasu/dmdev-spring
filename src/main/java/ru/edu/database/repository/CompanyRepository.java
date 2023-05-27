package ru.edu.database.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ru.edu.database.entity.Company;

@org.springframework.stereotype.Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

  // уже реализованы в JpaRepository
//  Optional<Company> findById(Integer id);

  // уже реализованы в JpaRepository
//  void delete(Company id);

}
