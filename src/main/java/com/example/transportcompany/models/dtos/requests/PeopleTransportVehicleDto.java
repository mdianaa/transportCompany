package com.example.transportcompany.models.dtos.requests;

import com.example.transportcompany.utils.enums.VehicleTypeTransportPeople;
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
public class PeopleTransportVehicleDto {

    @NotNull(message = "Registration number should be a valid string")
    private String registrationNumber;

    @NotNull(message = "Model should be a valid string")
    private String model;

    @NotNull(message = "Engine should be a valid string")
    private String engine;

    @Enumerated(EnumType.STRING)
    private VehicleTypeTransportPeople vehicleType;
}
