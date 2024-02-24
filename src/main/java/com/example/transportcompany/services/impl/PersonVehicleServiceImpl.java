package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.PeopleTransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.PeopleTransportVehicleDto;
import com.example.transportcompany.models.entities.PeopleTransportCompany;
import com.example.transportcompany.models.entities.PeopleTransportVehicle;
import com.example.transportcompany.repositories.PeopleTransportVehicleRepository;
import com.example.transportcompany.repositories.TransportCompanyRepository;
import com.example.transportcompany.services.PersonVehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class PersonVehicleServiceImpl implements PersonVehicleService {

    private final PeopleTransportVehicleRepository peopleTransportVehicleRepository;
    private final TransportCompanyRepository transportCompanyRepository;
    private final ModelMapper mapper;

    @Autowired
    public PersonVehicleServiceImpl(PeopleTransportVehicleRepository peopleTransportVehicleRepository, TransportCompanyRepository transportCompanyRepository,ModelMapper modelMapper) {
        this.peopleTransportVehicleRepository = peopleTransportVehicleRepository;
        this.transportCompanyRepository = transportCompanyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerPersonVehicle(PeopleTransportVehicleDto peopleTransportVehicleDto, PeopleTransportCompanyRequestDto company) {
        if (peopleTransportVehicleRepository.findByRegistrationNumber(peopleTransportVehicleDto.getRegistrationNumber()).isEmpty()) {
            PeopleTransportVehicle vehicle = mapper.map(peopleTransportVehicleDto, PeopleTransportVehicle.class);
            peopleTransportVehicleRepository.saveAndFlush(vehicle);

            if (company.getVehicles() == null) {
                company.setVehicles(new HashSet<>());
            }

            company.getVehicles().add(vehicle);

            PeopleTransportCompany peopleTransportCompany = mapper.map(company, PeopleTransportCompany.class);
            transportCompanyRepository.saveAndFlush(peopleTransportCompany);

            return "Successfully registered vehicle";
        }

        return "Vehicle with this registration number already exists!";
    }

    @Override
    public String editRegistrationNumber(String registrationNumber, String newRegistrationNumber) {
        if (peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            PeopleTransportVehicle vehicle = peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();

            PeopleTransportVehicleDto peopleTransportVehicle = new PeopleTransportVehicleDto(newRegistrationNumber, vehicle.getModel(), vehicle.getEngine(), vehicle.getVehicleType());
            vehicle = mapper.map(peopleTransportVehicle, PeopleTransportVehicle.class);
            peopleTransportVehicleRepository.saveAndFlush(vehicle);

            return "Successfully changed vehicle's registration number";
        }

        return "Cannot find a vehicle with this registration number!";
    }

    @Override
    public String editVehicleEngine(String registrationNumber, String newEngine) {
        if (peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            PeopleTransportVehicle vehicle = peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();

            PeopleTransportVehicleDto peopleTransportVehicle = new PeopleTransportVehicleDto(vehicle.getRegistrationNumber(), vehicle.getModel(), newEngine, vehicle.getVehicleType());
            vehicle = mapper.map(peopleTransportVehicle, PeopleTransportVehicle.class);
            peopleTransportVehicleRepository.saveAndFlush(vehicle);

            return "Successfully changed vehicle's engine";
        }

        return "Cannot find a vehicle with this registration number!";
    }

    @Override
    public PeopleTransportVehicle getVehicleByRegistrationNumber(String registrationNumber) {
        return peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
    }

    @Override
    public String deleteVehicle(String registrationNumber) {
        if (peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).isPresent()) {
            PeopleTransportVehicle vehicle = peopleTransportVehicleRepository.findByRegistrationNumber(registrationNumber).get();
            peopleTransportVehicleRepository.delete(vehicle);

            return "Successfully deleted vehicle";
        }

        return "Cannot find a vehicle with this registration number!";
    }
}
