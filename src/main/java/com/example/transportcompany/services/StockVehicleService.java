package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.StockTransportVehicleDto;
import com.example.transportcompany.models.entities.StockTransportVehicle;

public interface StockVehicleService {

    String registerStockVehicle(StockTransportVehicleDto stockTransportVehicleDto, CompanyRequestDto company);

    String editStockVehicle(long vehiclesId, StockTransportVehicleDto stockTransportVehicleDto);

    StockTransportVehicle getVehicleByRegistrationNumber(String registrationNumber);

    String deleteVehicle(String registrationNumber);
}
