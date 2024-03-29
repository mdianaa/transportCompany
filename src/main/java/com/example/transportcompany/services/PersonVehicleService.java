package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.PeopleTransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.PeopleTransportVehicleDto;
import com.example.transportcompany.models.entities.PeopleTransportVehicle;

public interface PersonVehicleService {
    String registerPersonVehicle(PeopleTransportVehicleDto peopleTransportVehicleDto, PeopleTransportCompanyRequestDto company);
    String editRegistrationNumber(String registrationNumber, String newRegistrationNumber);
    String editVehicleEngine(String registrationNumber, String newEngine);

    PeopleTransportVehicle getVehicleByRegistrationNumber(String registrationNumber);

    String deleteVehicle(String registrationNumber);
}
