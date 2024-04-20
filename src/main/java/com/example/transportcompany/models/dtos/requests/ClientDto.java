package com.example.transportcompany.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @NotNull(message = "Name should be a valid string")
    private String name;

    @NotNull(message = "Phone number should be a valid string")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Email should be a valid string")
    @Email()
    private String email;

    //    Set<Vehicle> getAllVehicles(String name);
    //
    //    Set<Load> getAllLoadForTransportation(String name);
}
