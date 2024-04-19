package com.example.transportcompany.models.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
//@DiscriminatorValue("STOCK_TRANSPORT_COMPANY")
public class StockTransportationCompany extends Company {

    @OneToMany(mappedBy = "company", targetEntity = Vehicle.class)
    private Set<StockTransportVehicle> vehicles;
}
