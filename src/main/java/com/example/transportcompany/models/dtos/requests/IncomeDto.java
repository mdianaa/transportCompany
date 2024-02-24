package com.example.transportcompany.models.dtos.requests;


import com.example.transportcompany.utils.enums.Month;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Month should be a valid month of the year")
    private Month month;

    private BigDecimal monthlyIncome;
}
