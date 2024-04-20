package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.*;
import com.example.transportcompany.models.entities.Client;

import java.util.Set;

public interface ClientService {

    String registerClient(ClientDto clientDto, CompanyRequestDto company, TransportationRequestDto transportationDto);

    String editClient(long clientId, ClientDto clientDto);

    String payTransportationPrice(long clientId, long transportationId);

    String sendPeople(long clientId, Set<PersonDto> peopleDtos, TransportationRequestDto transportationRequestDto);

    String sendStock(long clientId, Set<StockDto> stockDtos, TransportationRequestDto transportationRequestDto);

//    Set<Client> getAllClientsInCompany(CompanyRequestDto companyDto);

    String deleteClient(long clientId);
}
