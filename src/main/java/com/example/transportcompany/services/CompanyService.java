package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.ClientDto;
import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.entities.*;

import java.util.List;
import java.util.Set;

public interface CompanyService {
    String registerCompany(CompanyRequestDto companyRequestDto);

    String editCompany(long companyId, CompanyRequestDto companyRequestDto);

    Company getCompanyByName(String name);

    List<Company> getAllCompanies();

    String getTotalCountOfTransportations(String name);

    String getTotalPriceOfAllTransportations(String name);

    String getAllEmployeesWithTheirTransportationsCount(String name);

    String getEachEmployeeCurrentIncome(String name);

    String deleteCompany(String name);
}
