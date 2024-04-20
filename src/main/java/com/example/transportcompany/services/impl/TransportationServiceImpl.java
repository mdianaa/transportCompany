package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.TransportationRepository;
import com.example.transportcompany.services.TransportationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public TransportationServiceImpl(TransportationRepository transportationRepository, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.transportationRepository = transportationRepository;
        this.companyRepository = companyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerTransportation(TransportationRequestDto transportationRequestDto, CompanyRequestDto company) {
        Transportation transportation = mapper.map(transportationRequestDto, Transportation.class);

        Company transportCompany = companyRepository.findByName(company.getName()).get();

        mapper.map(company, transportCompany);

        transportation.setCompany(transportCompany);

        companyRepository.save(transportCompany);
        transportationRepository.save(transportation);

        return "Successfully registered a transportation";
    }

    @Override
    public String editTransportation(long transportationId, TransportationRequestDto transportationRequestDto) {
        if (transportationRepository.findById(transportationId).isPresent()) {
            Transportation transportation = transportationRepository.findById(transportationId).get();

            mapper.map(transportationRequestDto, transportation);
            transportationRepository.save(transportation);

            return "Successfully edited transportation";
        }
        return "Cannot find a transportation with this name!";
    }

    @Override
    public Transportation getTransportationByDestination(String endPoint) {
        return transportationRepository.findByEndPoint(endPoint).get();
    }

    @Override
    public List<Transportation> getAllTransportationsForCompany(String companyName) {
        return transportationRepository.findAllTransportationsByCompanyName(companyName);
    }

    @Override
    public String calculateTotalTransportPrice(long id) {
        if (transportationRepository.findById(id).isPresent()) {
            Transportation transportation = transportationRepository.findById(id).get();

            int quantity = transportation.getLoad().size();

            transportationRepository.saveAndFlush(transportation);

            return "Successfully calculated the total transportation price for the whole load";
        }
        return "Cannot find a transportation with this name!";
    }

    @Override
    public String deleteTransportation(long transportationId) {
        if (transportationRepository.findById(transportationId).isPresent()) {
            Transportation transportation = transportationRepository.findById(transportationId).get();
            transportationRepository.delete(transportation);

            return "Successfully deleted client";
        }

        return "Cannot find a client with this id!";
    }
}
