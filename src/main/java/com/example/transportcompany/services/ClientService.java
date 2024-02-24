package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.ClientDto;
import com.example.transportcompany.models.dtos.requests.PersonDto;
import com.example.transportcompany.models.dtos.requests.StockDto;
import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;

import java.util.Set;

public interface ClientService {
    String registerClient(ClientDto clientDto, TransportCompanyRequestDto company);
    String editClientName(String name, String newName);
    String editClientPhoneNumber(String name, String newPhoneNumber);
    String editClientEmail(String name, String newEmail);
    String payTransportationPrice(String name, long transportationId);
    String sendPeople(String name, Set<PersonDto> peopleDto);
    String sendStock(String name, Set<StockDto> stock);
    String deleteClient(String name);
}
