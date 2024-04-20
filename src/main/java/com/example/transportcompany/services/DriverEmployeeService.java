package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.EmployeeDto;
import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.DriverEmployee;
import com.example.transportcompany.models.entities.Vehicle;

import java.util.List;
import java.util.Set;

public interface DriverEmployeeService {

    String registerEmployee(EmployeeDto employeeDto, CompanyRequestDto company, Vehicle vehicle);

    String editEmployee(long employeeId, EmployeeDto employeeDto);

    Set<DriverEmployee> getAllEmployeesInCompany(String companyName);

    String commitTransportation(EmployeeDto employeeDto, TransportationRequestDto transportationDto);

    String deleteEmployee(long driverId);
}
