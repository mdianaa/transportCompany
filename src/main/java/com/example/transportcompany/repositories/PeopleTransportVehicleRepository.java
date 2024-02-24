package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.PeopleTransportVehicle;
import com.example.transportcompany.models.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleTransportVehicleRepository extends JpaRepository<PeopleTransportVehicle, Long> {
    Optional<PeopleTransportVehicle> findByRegistrationNumber(String registrationNumber);
}
