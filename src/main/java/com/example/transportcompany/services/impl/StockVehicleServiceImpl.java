package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.StockTransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.StockTransportVehicleDto;
import com.example.transportcompany.models.entities.StockTransportationCompany;
import com.example.transportcompany.models.entities.StockTransportVehicle;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.repositories.StockTransportVehicleRepository;
import com.example.transportcompany.repositories.TransportCompanyRepository;
import com.example.transportcompany.services.StockVehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class StockVehicleServiceImpl implements StockVehicleService {

    private final StockTransportVehicleRepository stockTransportVehicleRepository;
    private final TransportCompanyRepository transportCompanyRepository;
    private final ModelMapper mapper;

    public StockVehicleServiceImpl(StockTransportVehicleRepository stockTransportVehicleRepository, TransportCompanyRepository transportCompanyRepository, ModelMapper modelMapper) {
        this.stockTransportVehicleRepository = stockTransportVehicleRepository;
        this.transportCompanyRepository = transportCompanyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerStockVehicle(StockTransportVehicleDto stockTransportVehicleDto, StockTransportCompanyRequestDto company) {
        if (stockTransportVehicleRepository.findByRegistrationNumber(stockTransportVehicleDto.getRegistrationNumber()).isEmpty()) {
            StockTransportVehicle vehicle = mapper.map(stockTransportVehicleDto, StockTransportVehicle.class);
            stockTransportVehicleRepository.saveAndFlush(vehicle);

            if (company.getVehicles() == null) {
                company.setVehicles(new HashSet<>());
            }
            company.getVehicles().add(vehicle);

            StockTransportationCompany stockTransportCompany = mapper.map(company, StockTransportationCompany.class);
            Company transportCompany = transportCompanyRepository.findByName(stockTransportCompany.getName()).get();
            mapper.map(transportCompany, stockTransportCompany);

            transportCompanyRepository.saveAndFlush(transportCompany);

            return "Successfully registered vehicle";
        }

        return "Vehicle with this registration number already exists!";
    }

    @Override
    public String editRegistrationNumber(String registrationNumber, String newRegistrationNumber) {
        if (stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            StockTransportVehicle vehicle = stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();

            StockTransportVehicleDto stockTransportVehicleDto = new StockTransportVehicleDto(newRegistrationNumber, vehicle.getModel(), vehicle.getEngine(), vehicle.getVehicleType());
            vehicle = mapper.map(stockTransportVehicleDto, StockTransportVehicle.class);
            stockTransportVehicleRepository.saveAndFlush(vehicle);

            return "Successfully changed vehicle's registration number";
        }

        return "Cannot find a vehicle with this registration number!";
    }

    @Override
    public String editVehicleEngine(String registrationNumber, String newEngine) {
        if (stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            StockTransportVehicle vehicle = stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();

            StockTransportVehicleDto stockTransportVehicleDto = new StockTransportVehicleDto(vehicle.getRegistrationNumber(), vehicle.getModel(), newEngine, vehicle.getVehicleType());
            vehicle = mapper.map(stockTransportVehicleDto, StockTransportVehicle.class);
            stockTransportVehicleRepository.saveAndFlush(vehicle);

            return "Successfully changed vehicle's engine";
        }

        return "Cannot find a vehicle with this registration number!";
    }

    @Override
    public StockTransportVehicle getVehicleByRegistrationNumber(String registrationNumber) {
        return stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
    }

    @Override
    public String deleteVehicle(String registrationNumber) {
        if (stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            StockTransportVehicle vehicle = stockTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
            stockTransportVehicleRepository.delete(vehicle);

            return "Successfully deleted vehicle";
        }

        return "Cannot find a vehicle with this registration number!";
    }

}
