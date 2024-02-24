package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.VehicleTypeTransportStock;
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
@DiscriminatorValue("STOCK_TRANSPORT_VEHICLE")
public class StockTransportVehicle extends Vehicle {

    @Enumerated(EnumType.STRING)
    private VehicleTypeTransportStock vehicleType;

}
