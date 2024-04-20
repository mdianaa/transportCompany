package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Client;
import com.example.transportcompany.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.CookieHandler;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByName(String name);

    Optional<Client> findByEmail(String email);

//    Set<Client> findAllByCompany(Company company);
}
