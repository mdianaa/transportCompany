package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.repositories.TransportCompanyRepository;
import com.example.transportcompany.repositories.TransportationRepository;
import com.example.transportcompany.services.TransportationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;
    private final TransportCompanyRepository transportCompanyRepository;
    private final ModelMapper mapper;

    @Autowired
    public TransportationServiceImpl(TransportationRepository transportationRepository, TransportCompanyRepository transportCompanyRepository, ModelMapper modelMapper) {
        this.transportationRepository = transportationRepository;
        this.transportCompanyRepository = transportCompanyRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerTransportation(TransportationRequestDto transportationRequestDto, TransportCompanyRequestDto company) {
        Transportation transportation = mapper.map(transportationRequestDto, Transportation.class);

        // Find the TransportCompany by name
        Company transportCompany = transportCompanyRepository.findByName(company.getName()).orElse(null);

        if (transportCompany == null) {
            // If the company doesn't exist, create a new one and map the data
            transportCompany = mapper.map(company, Company.class);
        } else {
            // If the company already exists, update its data with the new values
            mapper.map(company, transportCompany);
        }

        transportation.setCompany(transportCompany);

        transportationRepository.saveAndFlush(transportation);

        return "Successfully registered a transportation";
    }

    @Override
    public String editDepartureDate(long id, LocalDate newDepartureDate) {
        if (transportationRepository.findById(id).isPresent()) {
            Transportation transportation = transportationRepository.findById(id).get();

            TransportationRequestDto transportationRequestDto = new TransportationRequestDto(transportation.getStartPoint(), transportation.getEndPoint(),
                    newDepartureDate, transportation.getArrivalDate(), transportation.getTransportationPricePerUnit());

            transportation = mapper.map(transportationRequestDto, Transportation.class);
            transportationRepository.saveAndFlush(transportation);

            return "Successfully edited departure date";
        }
        return "Cannot find a transportation with this name!";
    }

    @Override
    public String editArrivalDate(long id, LocalDate newArrivalDate) {
        if (transportationRepository.findById(id).isPresent()) {
            Transportation transportation = transportationRepository.findById(id).get();

            TransportationRequestDto transportationRequestDto = new TransportationRequestDto(transportation.getStartPoint(), transportation.getEndPoint(),
                    transportation.getDepartureDate(), newArrivalDate, transportation.getTransportationPricePerUnit());

            transportation = mapper.map(transportationRequestDto, Transportation.class);
            transportationRepository.saveAndFlush(transportation);

            return "Successfully edited arrival date";
        }
        return "Cannot find a transportation with this name!";
    }

    @Override
    public String editPricePerUnit(long id, BigDecimal newPrice) {
        if (transportationRepository.findById(id).isPresent()) {
            Transportation transportation = transportationRepository.findById(id).get();

            TransportationRequestDto transportationRequestDto = new TransportationRequestDto(transportation.getStartPoint(), transportation.getEndPoint(),
                    transportation.getDepartureDate(), transportation.getArrivalDate(), newPrice);

            transportation = mapper.map(transportationRequestDto, Transportation.class);
            transportationRepository.saveAndFlush(transportation);

            return "Successfully edited price per unit for the transportation";
        }
        return "Cannot find a transportation with this name!";
    }

    @Override
    public Transportation getTransportationByDestination(String endPoint) {
        return transportationRepository.findByEndPoint(endPoint).get();
    }

    @Override
    public List<Transportation> getAllTransportations() {
        return transportationRepository.findAll();
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
}
