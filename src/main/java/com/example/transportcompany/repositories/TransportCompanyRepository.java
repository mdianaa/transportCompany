package com.example.transportcompany.repositories;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.entities.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {
    Optional<TransportCompany> findByName(String name);
}
