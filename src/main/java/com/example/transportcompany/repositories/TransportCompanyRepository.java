package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportCompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
