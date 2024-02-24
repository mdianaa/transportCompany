package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.EmployeeDto;
import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportCompanyResponseDto;
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
            Employee employee = mapper.map(employeeDto, Employee.class);
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            if (transportCompanyResponseDto.getEmployees() == null) {
                transportCompanyResponseDto.setEmployees(new HashSet<>());
            }
            transportCompanyResponseDto.getEmployees().add(employee);

            employee.setVehicle(vehicle);

            employeeRepository.saveAndFlush(employee);

            TransportCompany transportCompany = mapper.map(company, TransportCompany.class);
            transportCompanyRepository.saveAndFlush(transportCompany);

            return "Successfully registered employee";
        }

        return "Employee with this name already exists!";
    }

    @Override
    public String editEmployeeName(String name, String newName) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(newName, employee.getPhoneNumber(), employee.getEmail(), employee.getSalary(), employee.getQualification());
            employee = mapper.map(employeeDto, Employee.class);
            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's name";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeePhoneNumber(String name, String newPhoneNumber) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(employee.getName(), newPhoneNumber, employee.getEmail(), employee.getSalary(), employee.getQualification());
            employee = mapper.map(employeeDto, Employee.class);
            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's phone number";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeEmail(String name, String newEmail) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(employee.getName(), employee.getPhoneNumber(), newEmail, employee.getSalary(), employee.getQualification());
            employee = mapper.map(employeeDto, Employee.class);
            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's email";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeSalary(String name, BigDecimal newSalary) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            EmployeeDto employeeDto = new EmployeeDto(employee.getName(), employee.getPhoneNumber(), employee.getEmail(), newSalary, employee.getQualification());
            employee = mapper.map(employeeDto, Employee.class);
            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's salary";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeVehicle(String name, Vehicle vehicle) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            employee.setVehicle(vehicle);

            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's vehicle";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String editEmployeeQualification(String name, DriverQualification qualification) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();

            employee.setQualification(qualification);

            employeeRepository.saveAndFlush(employee);

            return "Successfully changed employee's qualification";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public List<Employee> getAllEmployeesInCompany(String name) {
        return employeeRepository.findAll();
    }

    @Override
    public String commitNewTransportation(String name, Transportation transportation) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();



            employee.getTransportations().add(transportation);

            employeeRepository.saveAndFlush(employee);

            return "Successfully committed new transportation";
        }

        return "Cannot find an employee with this name!";
    }

    @Override
    public String deleteEmployee(String name) {
        if (employeeRepository.findByName(name).isPresent()) {
            Employee employee = employeeRepository.findByName(name).get();
            employeeRepository.delete(employee);

            return "Successfully deleted employee";
        }

        return "Cannot find an employee with this name!";
    }

//    private boolean validateVehicleTypeToQualification(Vehicle vehicle, DriverQualification qualification) {
//
//    }
}
