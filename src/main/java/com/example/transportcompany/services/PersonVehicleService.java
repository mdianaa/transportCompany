package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.PeopleTransportVehicleDto;
import com.example.transportcompany.models.entities.PersonTransportVehicle;

public interface PersonVehicleService {

    String registerPersonVehicle(PeopleTransportVehicleDto peopleTransportVehicleDto, CompanyRequestDto company);

    String editPersonVehicle(long vehicleId, PeopleTransportVehicleDto peopleTransportVehicleDto);

    PersonTransportVehicle getVehicleByRegistrationNumber(String registrationNumber);

    String deleteVehicle(String registrationNumber);
}
