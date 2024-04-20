package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.PeopleTransportVehicleDto;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.PersonTransportVehicle;
import com.example.transportcompany.models.entities.StockTransportVehicle;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.PersonTransportVehicleRepository;
import com.example.transportcompany.services.PersonVehicleService;
import com.example.transportcompany.utils.enums.CompanyType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class PersonVehicleServiceImpl implements PersonVehicleService {

    private final PersonTransportVehicleRepository personTransportVehicleRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public PersonVehicleServiceImpl(PersonTransportVehicleRepository personTransportVehicleRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.personTransportVehicleRepository = personTransportVehicleRepository;
        this.companyRepository = companyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerPersonVehicle(PeopleTransportVehicleDto peopleTransportVehicleDto, CompanyRequestDto company) {
        if (personTransportVehicleRepository.findByRegistrationNumber(peopleTransportVehicleDto.getRegistrationNumber()).isEmpty()) {

            if (!company.getType().equals(CompanyType.PERSON_TRANSPORT_COMPANY)) {
                return "Unable to register person transport vehicle to a stock transport company";
            }

            PersonTransportVehicle vehicle = mapper.map(peopleTransportVehicleDto, PersonTransportVehicle.class);
            Company personTransportCompany = companyRepository.findByName(company.getName()).get();

            vehicle.setCompany(personTransportCompany);

            personTransportVehicleRepository.saveAndFlush(vehicle);

            if (personTransportCompany.getVehicles() == null) {
                personTransportCompany.setVehicles(new HashSet<>());
            }

            personTransportCompany.getVehicles().add(vehicle);

            companyRepository.save(personTransportCompany);

            return "Successfully registered vehicle";
        }

        return "Vehicle with this registration number already exists!";
    }

    @Override
    public String editPersonVehicle(long vehicleId, PeopleTransportVehicleDto peopleTransportVehicleDto) {
        if (personTransportVehicleRepository.findById(vehicleId).isPresent()) {
            PersonTransportVehicle vehicle = personTransportVehicleRepository.findById(vehicleId).get();

            mapper.map(peopleTransportVehicleDto, vehicle);
            personTransportVehicleRepository.saveAndFlush(vehicle);

            return "Successfully changed vehicle's registration number";
        }

        return "Cannot find a vehicle with this id!";
    }

    @Override
    public PersonTransportVehicle getVehicleByRegistrationNumber(String registrationNumber) {
        return personTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
    }

    @Override
    public String deleteVehicle(String registrationNumber) {
        if (personTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            PersonTransportVehicle vehicle = personTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
            personTransportVehicleRepository.delete(vehicle);

            return "Successfully deleted vehicle";
        }

        return "Cannot find a vehicle with this registration number!";
    }
}
