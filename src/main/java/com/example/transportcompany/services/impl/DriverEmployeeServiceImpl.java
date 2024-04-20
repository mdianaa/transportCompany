package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.EmployeeDto;
import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.*;
import com.example.transportcompany.repositories.DriverEmployeeRepository;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.TransportationRepository;
import com.example.transportcompany.services.DriverEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DriverEmployeeServiceImpl implements DriverEmployeeService {

    private final DriverEmployeeRepository driverEmployeeRepository;
    private final CompanyRepository companyRepository;

    private final TransportationRepository transportationRepository;
    private final ModelMapper mapper;

    @Autowired
    public DriverEmployeeServiceImpl(DriverEmployeeRepository driverEmployeeRepository, CompanyRepository companyRepository, TransportationRepository transportationRepository, ModelMapper modelMapper) {
        this.driverEmployeeRepository = driverEmployeeRepository;
        this.companyRepository = companyRepository;
        this.transportationRepository = transportationRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerEmployee(EmployeeDto employeeDto, CompanyRequestDto company, Vehicle vehicle) {
        if (driverEmployeeRepository.findByName(employeeDto.getName()).isEmpty()) {
            DriverEmployee driverEmployee = mapper.map(employeeDto, DriverEmployee.class);
            Company transportCompany = companyRepository.findByName(company.getName()).orElse(null);

            // The company must already exist - update its data with the new values
                mapper.map(company, transportCompany);

            if (transportCompany.getDriverEmployees() == null) {
                transportCompany.setDriverEmployees(new HashSet<>());
            }
            transportCompany.getDriverEmployees().add(driverEmployee);

            // check whether the vehicle is not already in use by another driver
            // check whether the driver has the qualification to drive the current vehicle
            if (vehicle.getDriverEmployee() == null && driverEmployee.getQualification().equals(vehicle.getNeededQualification())) {
                driverEmployee.setVehicle(vehicle);
            }

            //TODO: get a dto vehicle with no driver and set it to the current driver

            driverEmployee.setCompany(transportCompany);

            driverEmployeeRepository.save(driverEmployee);

            return "Successfully registered employee";
        }

        return "Employee with this name already exists!";
    }

    @Override
    public String editEmployee(long employeeId, EmployeeDto employeeDto) {
        if (driverEmployeeRepository.findById(employeeId).isPresent()) {
            DriverEmployee driverEmployee = driverEmployeeRepository.findById(employeeId).get();

            mapper.map(employeeDto, driverEmployee);
            driverEmployeeRepository.save(driverEmployee);

            return "Successfully changed employee's information.";
        }

        return "Cannot find an employee with this id!";
    }

    @Override
    public Set<DriverEmployee> getAllEmployeesInCompany(String companyName) {
        return driverEmployeeRepository.findAllDriversByCompanyName(companyName);
    }

    @Override
    public String commitTransportation(EmployeeDto employeeDto, TransportationRequestDto transportationDto) {
        if (driverEmployeeRepository.findByEmail(employeeDto.getEmail()).isPresent()
            && transportationRepository.findByEndPoint(transportationDto.getEndPoint()).isPresent()) {

            DriverEmployee driverEmployee = driverEmployeeRepository.findByEmail(employeeDto.getEmail()).get();
            Transportation transportation = transportationRepository.findByEndPoint(transportationDto.getEndPoint()).get();

            driverEmployee.getTransportations().add(transportation);

            driverEmployeeRepository.save(driverEmployee);

            return "Successfully committed new transportation.";
        }

        return "Cannot find an employee with this email!";
    }

    @Override
    public String deleteEmployee(long employeeId) {
        if (driverEmployeeRepository.findById(employeeId).isPresent()) {
            DriverEmployee driverEmployee = driverEmployeeRepository.findById(employeeId).get();
            driverEmployeeRepository.delete(driverEmployee);

            return "Successfully deleted employee";
        }

        return "Cannot find an employee with this id!";
    }
}
