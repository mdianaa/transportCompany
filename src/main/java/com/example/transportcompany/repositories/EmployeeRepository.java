package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.DriverEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<DriverEmployee, Long> {

    Optional<DriverEmployee> findByName(String name);
}
