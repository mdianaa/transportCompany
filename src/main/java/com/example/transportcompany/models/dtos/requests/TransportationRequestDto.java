package com.example.transportcompany.models.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportationRequestDto {

    @NotNull(message = "Start point should be a valid string")
    private String startPoint;

    @NotNull(message = "End point should be a valid string")
    private String endPoint;

    @NotNull(message = "Departure date should be a valid date")
    private LocalDate departureDate;

    @NotNull(message = "Arrival date should be a valid date")
    private LocalDate arrivalDate;

    @NotNull(message = "Price per unit must have a valid value")
    private BigDecimal transportationPricePerUnit;

}
