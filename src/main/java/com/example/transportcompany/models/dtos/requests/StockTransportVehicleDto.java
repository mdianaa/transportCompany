package com.example.transportcompany.models.dtos.requests;

import com.example.transportcompany.utils.enums.VehicleTypeTransportStock;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTransportVehicleDto {

    @NotNull(message = "Registration number should be a valid string")
    private String registrationNumber;

    @NotNull(message = "Model should be a valid string")
    private String model;

    @NotNull(message = "Engine should be a valid string")
    private String engine;

    @Enumerated(EnumType.STRING)
    private VehicleTypeTransportStock vehicleType;

}
