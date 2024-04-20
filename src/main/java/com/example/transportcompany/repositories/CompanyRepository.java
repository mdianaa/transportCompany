package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);

}
