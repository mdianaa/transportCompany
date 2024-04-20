package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.VehicleTypeForPeopleTransportation;
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
public class PersonTransportVehicle extends Vehicle {

    @Enumerated(EnumType.STRING)
    private VehicleTypeForPeopleTransportation personVehicleType;

}
