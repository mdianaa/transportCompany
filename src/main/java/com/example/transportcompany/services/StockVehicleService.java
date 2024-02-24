package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.StockTransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.StockTransportVehicleDto;
import com.example.transportcompany.models.entities.StockTransportVehicle;

public interface StockVehicleService {
    String registerStockVehicle(StockTransportVehicleDto stockTransportVehicleDto, StockTransportCompanyRequestDto company);
    String editRegistrationNumber(String registrationNumber, String newRegistrationNumber);
    String editVehicleEngine(String registrationNumber, String newEngine);

    StockTransportVehicle getVehicleByRegistrationNumber(String registrationNumber);

    String deleteVehicle(String registrationNumber);
}
