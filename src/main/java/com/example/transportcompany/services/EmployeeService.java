package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.EmployeeDto;
import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.entities.DriverEmployee;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.models.entities.Vehicle;
import com.example.transportcompany.utils.enums.DriverQualification;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    String registerEmployee(EmployeeDto employeeDto, TransportCompanyRequestDto company, Vehicle vehicle);
    String editEmployeeName(String name, String newName);
    String editEmployeePhoneNumber(String name, String newPhoneNumber);
    String editEmployeeEmail(String name, String newEmail);
    String editEmployeeSalary(String name, BigDecimal newSalary);
    String editEmployeeVehicle(String name, Vehicle vehicle);
    String editEmployeeQualification(String name, DriverQualification qualification);
    List<DriverEmployee> getAllEmployeesInCompany(String name);
    String commitNewTransportation(String name, Transportation transportation);
    String deleteEmployee(String name);
}
