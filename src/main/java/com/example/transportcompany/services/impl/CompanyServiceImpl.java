package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportCompanyResponseDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportationResponseDto;
import com.example.transportcompany.models.entities.DriverEmployee;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.models.entities.Vehicle;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerCompany(CompanyRequestDto companyRequestDto) {
        if (companyRepository.findByName(companyRequestDto.getName()).isEmpty()) {
            Company company = mapper.map(companyRequestDto, Company.class);

            companyRepository.save(company);

            return "Successfully registered company";
        }

        return "Company with this name already exists!";
    }

    @Override
    public String editCompany(long companyId, CompanyRequestDto companyRequestDto) {
        if (companyRepository.findById(companyId).isPresent()) {
            Company company = companyRepository.findById(companyId).get();

            mapper.map(companyRequestDto, company);

            companyRepository.saveAndFlush(company);

            return "Successfully changed company's information";
        }

        return "Cannot find a company with this id!";
    }

    @Override
    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name).get();
    }


    @Override
    public List<Company> getAllCompanies() {
       return companyRepository.findAll();
    }

    @Override
    public String getTotalCountOfTransportations(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            Company company = companyRepository.findByName(name).get();
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            return "Total count of transportations: " + transportCompanyResponseDto.getTransportations().size();
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String getTotalPriceOfAllTransportations(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            Company company = companyRepository.findByName(name).get();

            List<BigDecimal> allPrices = new ArrayList<>();

            for (Transportation transportation : company.getTransportations()) {
                BigDecimal price = transportation.getTransportationPricePerUnit().multiply(BigDecimal.valueOf(transportation.getLoad().size()));
                allPrices.add(price);
            }

            BigDecimal totalPrice = new BigDecimal(0);
            for (BigDecimal currentPrice : allPrices) {
                totalPrice = totalPrice.add(currentPrice);
            }

            return "Total price of all transportations for company " + name + " : " + totalPrice;
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String getAllEmployeesWithTheirTransportationsCount(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            Company company = companyRepository.findByName(name).get();
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            StringBuilder sb = new StringBuilder();

            Set<DriverEmployee> driverEmployees = transportCompanyResponseDto.getDriverEmployees();
            for (DriverEmployee driverEmployee : driverEmployees) {
                sb.append(driverEmployee.getName()).append("has committed ").append(driverEmployee.getTransportations().size()).append(System.lineSeparator());
            }

            return sb.toString();
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String getEachEmployeeCurrentIncome(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            Company company = companyRepository.findByName(name).get();
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            StringBuilder sb = new StringBuilder();

            Set<DriverEmployee> driverEmployees = transportCompanyResponseDto.getDriverEmployees();
            for (DriverEmployee driverEmployee : driverEmployees) {
                sb.append(driverEmployee.getName()).append("has a total income of: ");

                BigDecimal totalEmployeeIncome = new BigDecimal(0);

                for (Transportation transportation : driverEmployee.getTransportations()) {
                    BigDecimal price = transportation.getTransportationPricePerUnit().multiply(BigDecimal.valueOf(transportation.getLoad().size()));
                    totalEmployeeIncome = totalEmployeeIncome.add(price);
                }

                sb.append(totalEmployeeIncome).append(System.lineSeparator());
            }

            return sb.toString();
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String deleteCompany(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            Company transportCompany = companyRepository.findByName(name).get();

            Company company = mapper.map(transportCompany, Company.class);
            companyRepository.delete(company);

            return "Successfully deleted company";
        }

        return "Cannot find a company with this name!";
    }
}
