package com.veterinaria.demo.repository;

import com.veterinaria.demo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
    SELECT c FROM Customer c
    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:phone IS NULL OR c.phone LIKE CONCAT('%', :phone, '%'))
      AND (:cpf IS NULL OR c.cpf LIKE CONCAT('%', :cpf, '%'))
      AND (:creationDate IS NULL OR c.creationDate = :creationDate)
""")
    List<Customer> findByFilter(String name, String phone, String cpf, LocalDate creationDate);
}
