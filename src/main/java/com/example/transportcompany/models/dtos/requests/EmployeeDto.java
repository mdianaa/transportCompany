package com.example.transportcompany.models.dtos.requests;

import com.example.transportcompany.utils.enums.DriverQualification;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotNull(message = "Name should be a valid string")
    private String name;

    @NotNull(message = "Phone number should be a valid string")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Email should be a valid string")
    @Email()
    private String email;

    @NotNull(message = "Salary should have a valid value")
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Qualification should be a valid driver's qualification")
    private DriverQualification qualification;

}
