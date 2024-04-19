package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.ClientDto;
import com.example.transportcompany.models.dtos.requests.PersonDto;
import com.example.transportcompany.models.dtos.requests.StockDto;
import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.entities.*;
import com.example.transportcompany.repositories.*;
import com.example.transportcompany.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final TransportCompanyRepository transportCompanyRepository;
    private final TransportationRepository transportationRepository;
    private final StockRepository stockRepository;
    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, TransportCompanyRepository transportCompanyRepository, TransportationRepository transportationRepository, StockRepository stockRepository, PersonRepository personRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.transportCompanyRepository = transportCompanyRepository;
        this.transportationRepository = transportationRepository;
        this.stockRepository = stockRepository;
        this.personRepository = personRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerClient(ClientDto clientDto, TransportCompanyRequestDto company) {
        if (clientRepository.findByName(clientDto.getName()).isEmpty()) {

            Client client = mapper.map(clientDto, Client.class);
            Company transportCompany = transportCompanyRepository.findByName(company.getName()).orElse(null);

            if (transportCompany == null) {
                // If the company doesn't exist, create a new one and map the data
                transportCompany = mapper.map(company, Company.class);
            } else {
                // If the company already exists, update its data with the new values
                mapper.map(company, transportCompany);
            }

            client.setCompany(transportCompany);
            clientRepository.saveAndFlush(client);


            return "Successfully registered client";
        }

        return "Client with this name already exists!";
    }

    @Override
    public String editClientName(String name, String newName) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();

            ClientDto clientDto = new ClientDto(newName, client.getPhoneNumber(), client.getEmail());

            mapper.map(client, clientDto);
            clientRepository.saveAndFlush(client);

            return "Successfully changed client's name";
        }

        return "Cannot find a client with this name!";
    }

    @Override
    public String editClientPhoneNumber(String name, String newPhoneNumber) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();

            ClientDto clientDto = new ClientDto(client.getName(), newPhoneNumber, client.getEmail());

            mapper.map(client, clientDto);
            clientRepository.saveAndFlush(client);

            return "Successfully changed client's phone number";
        }

        return "Cannot find a client with this name!";
    }

    @Override
    public String editClientEmail(String name, String newEmail) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();

            ClientDto clientDto = new ClientDto(client.getName(), client.getPhoneNumber(), newEmail);

            mapper.map(client, clientDto);
            clientRepository.saveAndFlush(client);

            return "Successfully changed client's email";
        }

        return "Cannot find a client with this name!";
    }

    @Override
    public String payTransportationPrice(String name, long transportationId) {
        Transportation transportation = transportationRepository.findById(transportationId).get();
        Client client = clientRepository.findByName(name).get();

        if (client != null && transportation.getClient().getName().equals(name)) {
            client.getTransportations().add(transportation);
            transportation.setPaid(true);

            clientRepository.saveAndFlush(client);
            transportationRepository.saveAndFlush(transportation);

            return "Successfully paid transportation price";
        }

        return "Unable to pay transportation: cannot find a client with this name/ wrong transportation!";
    }

    @Override
    public String sendPeople(String name, Set<PersonDto> peopleDto) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();

            for (PersonDto personDto : peopleDto) {
                Person person = mapper.map(personDto, Person.class);
                client.getLoad().add(person);
                personRepository.saveAndFlush(person);
            }

            clientRepository.saveAndFlush(client);

            return "Successfully registered people to be sent";
        }

        return "Cannot find a client with this name!";
    }

    @Override
    public String sendStock(String name, Set<StockDto> stocksDto) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();

            for (StockDto stockDto : stocksDto) {
                Stock stock = mapper.map(stockDto, Stock.class);
                client.getLoad().add(stock);
                stockRepository.saveAndFlush(stock);
            }

            clientRepository.saveAndFlush(client);

            return "Successfully registered stock to be sent";
        }

        return "Cannot find a client with this name!";
    }

    @Override
    public String deleteClient(String name) {
        if (clientRepository.findByName(name).isPresent()) {
            Client client = clientRepository.findByName(name).get();
            clientRepository.delete(client);

            return "Successfully deleted client";
        }

        return "Cannot find a client with this name!";
    }
}
