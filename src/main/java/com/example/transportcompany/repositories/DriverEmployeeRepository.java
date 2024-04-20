package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.DriverEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DriverEmployeeRepository extends JpaRepository<DriverEmployee, Long> {

    Optional<DriverEmployee> findByName(String name);

    Optional<DriverEmployee> findByEmail(String email);

    Set<DriverEmployee> findAllDriversByCompanyName(String companyName);

}
