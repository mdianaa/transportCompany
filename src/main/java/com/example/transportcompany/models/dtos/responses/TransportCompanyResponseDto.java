package com.example.transportcompany.models.dtos.responses;

import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.Client;
import com.example.transportcompany.models.entities.DriverEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportCompanyResponseDto {

    private Set<DriverEmployee> driverEmployees;
    private Set<Client> clients;
    private Set<TransportationRequestDto> transportations;

}
