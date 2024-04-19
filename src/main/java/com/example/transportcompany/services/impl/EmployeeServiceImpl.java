package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.EmployeeDto;
import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.entities.*;
import com.example.transportcompany.repositories.EmployeeRepository;
import com.example.transportcompany.repositories.TransportCompanyRepository;
import com.example.transportcompany.services.EmployeeService;
import com.example.transportcompany.utils.enums.DriverQualification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TransportCompanyRepository transportCompanyRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, TransportCompanyRepository transportCompanyRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.transportCompanyRepository = transportCompanyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerEmployee(EmployeeDto employeeDto, TransportCompanyRequestDto company, Vehicle vehicle) {
        if (employeeRepository.findByName(employeeDto.getName()).isEmpty()) {
            DriverEmployee driverEmployee = mapper.map(employeeDto, DriverEmployee.class);
            Company transportCompany = transportCompanyRepository.findByName(company.getName()).orElse(null);

            if (transportCompany == null) {
                // If the company doesn't exist, create a new one and map the data
                transportCompany = mapper.map(company, Company.class);
            } else {
                // If the company already exists, update its data with the new values
                mapper.map(company, transportCompany);
            }

            if (transportCompany.getDriverEmployees() == null) {
                transportCompany.setDriverEmployees(new HashSet<>());
            }
            transportCompany.getDriverEmployees().add(driverEmployee);

            driverEmployee.setVehicle(vehicle);
            driverEmployee.setCompany(transportCompany);

            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully registered employee";
        }

        return "Employee with this name already exists!";
    }

    @Override
    public String editEmployeeName(String name, String newName) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(newName, driverEmployee.getPhoneNumber(), driverEmployee.getEmail(), driverEmployee.getSalary(), driverEmployee.getQualification());

//            employee.setName(newName);

            mapper.map(driverEmployee, employeeDto);
            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's name";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeePhoneNumber(String name, String newPhoneNumber) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(driverEmployee.getName(), newPhoneNumber, driverEmployee.getEmail(), driverEmployee.getSalary(), driverEmployee.getQualification());

            mapper.map(driverEmployee, employeeDto);
            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's phone number";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeEmail(String name, String newEmail) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(driverEmployee.getName(), driverEmployee.getPhoneNumber(), newEmail, driverEmployee.getSalary(), driverEmployee.getQualification());

            mapper.map(driverEmployee, employeeDto);
            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's email";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeSalary(String name, BigDecimal newSalary) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(driverEmployee.getName(), driverEmployee.getPhoneNumber(), driverEmployee.getEmail(), newSalary, driverEmployee.getQualification());

            mapper.map(driverEmployee, employeeDto);
            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's salary";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeVehicle(String name, Vehicle vehicle) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            driverEmployee.setVehicle(vehicle);

            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's vehicle";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeQualification(String name, DriverQualification qualification) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();

            driverEmployee.setQualification(qualification);

            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully changed employee's qualification";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public List<DriverEmployee> getAllEmployeesInCompany(String name) {
        return employeeRepository.findAll();
    }

    @Override
    public String commitNewTransportation(String name, Transportation transportation) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();



            driverEmployee.getTransportations().add(transportation);

            employeeRepository.saveAndFlush(driverEmployee);

            return "Successfully committed new transportation";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String deleteEmployee(String name) {
        if (employeeRepository.findByName(name).isPresent()) {
            DriverEmployee driverEmployee = employeeRepository.findByName(name).get();
            employeeRepository.delete(driverEmployee);

            return "Successfully deleted employee";
        }

        return "Cannot find an employee with this name!";
    }

//    private boolean validateVehicleTypeToQualification(Vehicle vehicle, DriverQualification qualification) {
//
//    }
}
