package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.Transportation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransportationService {
    String registerTransportation(TransportationRequestDto transportationRequestDto, TransportCompanyRequestDto company);
    String editDepartureDate(long id, LocalDate newDepartureDate);
    String editArrivalDate(long id, LocalDate newArrivalDate);
    String editPricePerUnit(long id, BigDecimal newPrice);

    Transportation getTransportationByDestination(String endPoint);

    List<Transportation> getAllTransportations();
    String calculateTotalTransportPrice(long id);
}
