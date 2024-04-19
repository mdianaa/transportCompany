package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.entities.Company;

import java.util.List;

public interface TransportCompanyService {
    String registerCompany(TransportCompanyRequestDto transportCompanyRequestDto);
    String editCompanyName(String name, String newName);
    String editCompanyAddress(String name, String newAddress);
    String editCompanyPhoneNumber(String name, String newPhoneNumber);
    String editCompanyEmail(String name, String newEmail);

    Company getCompanyByName(String name);

    List<Company> getAllCompanies();

    String getTotalCountOfTransportations(String name);

    String getTotalPriceOfAllTransportations(String name);

    String getAllEmployeesWithTheirTransportationsCount(String name);

    String getEachEmployeeCurrentIncome(String name);

    String deleteCompany(String name);
}
