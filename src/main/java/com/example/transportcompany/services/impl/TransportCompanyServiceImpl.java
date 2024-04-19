package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportCompanyResponseDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportationResponseDto;
import com.example.transportcompany.models.entities.DriverEmployee;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.repositories.TransportCompanyRepository;
import com.example.transportcompany.services.TransportCompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TransportCompanyServiceImpl implements TransportCompanyService {

    private final TransportCompanyRepository transportCompanyRepository;
    private final ModelMapper mapper;

    @Autowired
    public TransportCompanyServiceImpl(TransportCompanyRepository transportCompanyRepository, ModelMapper modelMapper) {
        this.transportCompanyRepository = transportCompanyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerCompany(TransportCompanyRequestDto transportCompanyRequestDto) {
        if (transportCompanyRepository.findByName(transportCompanyRequestDto.getName()).isEmpty()) {
            Company company = mapper.map(transportCompanyRequestDto, Company.class);
            transportCompanyRepository.saveAndFlush(company);

            return "Successfully registered company";
        }

        return "Company with this name already exists!";
    }

    @Override
    public String editCompanyName(String name, String newName) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyRequestDto transportCompanyRequestDto = mapper.map(company, TransportCompanyRequestDto.class);

            transportCompanyRequestDto.setName(newName);

            company = mapper.map(transportCompanyRequestDto, Company.class);
            transportCompanyRepository.saveAndFlush(company);

            return "Successfully changed company's name";
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String editCompanyAddress(String name, String newAddress) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyRequestDto transportCompanyRequestDto = mapper.map(company, TransportCompanyRequestDto.class);

            transportCompanyRequestDto.setAddress(newAddress);

            company = mapper.map(transportCompanyRequestDto, Company.class);
            transportCompanyRepository.saveAndFlush(company);

            return "Successfully changed company's address";
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String editCompanyPhoneNumber(String name, String newPhoneNumber) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyRequestDto transportCompanyRequestDto = mapper.map(company, TransportCompanyRequestDto.class);

            transportCompanyRequestDto.setPhoneNumber(newPhoneNumber);

            company = mapper.map(transportCompanyRequestDto, Company.class);
            transportCompanyRepository.saveAndFlush(company);

            return "Successfully changed company's phone number";
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String editCompanyEmail(String name, String newEmail) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyRequestDto transportCompanyRequestDto = mapper.map(company, TransportCompanyRequestDto.class);

            transportCompanyRequestDto.setEmail(newEmail);

            company = mapper.map(transportCompanyRequestDto, Company.class);
            transportCompanyRepository.saveAndFlush(company);

            return "Successfully changed company's name company";
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public Company getCompanyByName(String name) {
        return transportCompanyRepository.findByName(name).get();
    }


    @Override
    public List<Company> getAllCompanies() {
       return transportCompanyRepository.findAll();
    }

    @Override
    public String getTotalCountOfTransportations(String name) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            return "Total count of transportations: " + transportCompanyResponseDto.getTransportations().size();
        }

        return "Cannot find a company with this name!";
    }

    @Override
    public String getTotalPriceOfAllTransportations(String name) {
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
            TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(company, TransportCompanyResponseDto.class);

            List<BigDecimal> allPrices = new ArrayList<>();

            for (TransportationRequestDto transportation : transportCompanyResponseDto.getTransportations()) {
                TransportationResponseDto transportationResponseDto = mapper.map(transportation, TransportationResponseDto.class);
                BigDecimal price = transportation.getTransportationPricePerUnit().multiply(BigDecimal.valueOf(transportationResponseDto.getLoad().size()));
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
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
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
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company company = transportCompanyRepository.findByName(name).get();
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
        if (transportCompanyRepository.findByName(name).isPresent()) {
            Company transportCompany = transportCompanyRepository.findByName(name).get();

            Company company = mapper.map(transportCompany, Company.class);
            transportCompanyRepository.delete(company);

            return "Successfully deleted company";
        }

        return "Cannot find a company with this name!";
    }
}
