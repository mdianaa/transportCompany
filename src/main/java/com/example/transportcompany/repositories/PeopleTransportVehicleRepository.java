package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.PersonTransportVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleTransportVehicleRepository extends JpaRepository<PersonTransportVehicle, Long> {
    Optional<PersonTransportVehicle> findByRegistrationNumber(String registrationNumber);
}
