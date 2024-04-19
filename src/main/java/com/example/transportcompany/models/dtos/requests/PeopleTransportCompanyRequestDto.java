package com.example.transportcompany.models.dtos.requests;

import com.example.transportcompany.models.entities.PersonTransportVehicle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PeopleTransportCompanyRequestDto extends TransportCompanyRequestDto {

    private Set<PersonTransportVehicle> vehicles;
    public PeopleTransportCompanyRequestDto(@NotNull(message = "Name should be a valid string") String name, @NotNull(message = "Address should be a valid string") String address, @NotNull(message = "Phone number should be a valid string") @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Phone number must be 10 digits") String phoneNumber, @NotNull(message = "Email should be a valid string") @Email() String email) {
        super(name, address, phoneNumber, email);
    }
}
