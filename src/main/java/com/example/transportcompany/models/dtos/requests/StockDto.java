package com.example.transportcompany.models.dtos.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    @NotNull(message = "Name should be a valid string")
    private String name;

    @NotNull(message = "Weight should be a valid value")
    @Digits(integer = 2, fraction = 1)
    private String weight;
}
