package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.entities.Transportation;

import java.util.List;

public interface TransportationService {

    String registerTransportation(TransportationRequestDto transportationRequestDto, CompanyRequestDto companyDto);

    String editTransportation(long transportationId, TransportationRequestDto transportationRequestDto);

    Transportation getTransportationByDestination(String endPoint);

    List<Transportation> getAllTransportationsForCompany(String companyName);

    String calculateTotalTransportPrice(long id);

    String deleteTransportation(long transportationId);
}
