package com.veterinaria.demo.repository;

import com.veterinaria.demo.enums.UserProfile;
import com.veterinaria.demo.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u FROM User u
    WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:crmv_number IS NULL OR u.crmv_number LIKE CONCAT('%', :crmv_number, '%'))
      AND (:profile IS NULL OR u.profile = :profile)
""")
    List<User> findByFilter(String name, String crmv_number, UserProfile profile);
}
