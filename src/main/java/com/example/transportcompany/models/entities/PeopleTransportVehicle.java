package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.VehicleTypeTransportPeople;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("PEOPLE_TRANSPORT_VEHICLE")
public class PeopleTransportVehicle extends Vehicle {

    @Enumerated(EnumType.STRING)
    private VehicleTypeTransportPeople vehicleType;

}
