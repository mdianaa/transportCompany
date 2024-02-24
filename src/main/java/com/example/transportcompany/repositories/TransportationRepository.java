package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    Optional<Transportation> findByEndPoint(String endPoint);
}
