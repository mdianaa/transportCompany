package com.example.transportcompany.repositories;

import com.example.transportcompany.models.entities.Load;
import com.example.transportcompany.models.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Load> {
}
