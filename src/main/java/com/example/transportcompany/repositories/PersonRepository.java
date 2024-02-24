package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
