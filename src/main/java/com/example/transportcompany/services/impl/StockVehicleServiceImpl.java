package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.StockTransportVehicleDto;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.StockTransportVehicle;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.StockTransportVehicleRepository;
import com.example.transportcompany.services.StockVehicleService;
import com.example.transportcompany.utils.enums.CompanyType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class StockVehicleServiceImpl implements StockVehicleService {

    private final StockTransportVehicleRepository stockTransportVehicleRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    public StockVehicleServiceImpl(StockTransportVehicleRepository stockTransportVehicleRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.stockTransportVehicleRepository = stockTransportVehicleRepository;
        this.companyRepository = companyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerStockVehicle(StockTransportVehicleDto stockTransportVehicleDto, CompanyRequestDto company) {
        if (stockTransportVehicleRepository.findByRegistrationNumber(stockTransportVehicleDto.getRegistrationNumber()).isEmpty()) {

            if (!company.getType().equals(CompanyType.STOCK_TRANSPORT_COMPANY)) {
                return "Unable to register stock transport vehicle to a person transport company";
            }

            StockTransportVehicle vehicle = mapper.map(stockTransportVehicleDto, StockTransportVehicle.class);
            Company stockTransportCompany = companyRepository.findByName(company.getName()).get();

            vehicle.setCompany(stockTransportCompany);

            stockTransportVehicleRepository.save(vehicle);


            if (stockTransportCompany.getVehicles() == null) {
                stockTransportCompany.setVehicles(new HashSet<>());
            }

            stockTransportCompany.getVehicles().add(vehicle);

            companyRepository.save(stockTransportCompany);

            return "Successfully registered vehicle";
        }

        return "Vehicle with this registration number already exists!";
    }

    @Override
    public String editStockVehicle(long vehicleId, StockTransportVehicleDto stockTransportVehicleDto) {
        if (stockTransportVehicleRepository.findById(vehicleId).isPresent()) {
            StockTransportVehicle vehicle = stockTransportVehicleRepository.findById(vehicleId).get();

            mapper.map(stockTransportVehicleDto, vehicle);
            stockTransportVehicleRepository.save(vehicle);

            return "Successfully changed vehicle's registration number";
        }

        return "Cannot find a vehicle with this id!";
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
