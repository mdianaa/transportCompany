package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.StockTransportVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockTransportVehicleRepository extends JpaRepository<StockTransportVehicle, Long> {
    Optional<StockTransportVehicle> findByRegistrationNumber(String registrationNumber);
}
