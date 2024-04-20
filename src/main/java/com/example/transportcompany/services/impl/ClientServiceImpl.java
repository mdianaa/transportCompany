package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.*;
import com.example.transportcompany.models.entities.*;
import com.example.transportcompany.repositories.*;
import com.example.transportcompany.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final TransportationRepository transportationRepository;
    private final StockRepository stockRepository;
    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CompanyRepository companyRepository, TransportationRepository transportationRepository, StockRepository stockRepository, PersonRepository personRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.transportationRepository = transportationRepository;
        this.stockRepository = stockRepository;
        this.personRepository = personRepository;
        this.mapper = modelMapper;
    }

    @Override
    public String registerClient(ClientDto clientDto, CompanyRequestDto company, TransportationRequestDto transportationDto) {
        if (clientRepository.findByName(clientDto.getName()).isEmpty()) {

            Client client = mapper.map(clientDto, Client.class);
            Company transportCompany = companyRepository.findByName(company.getName()).get();
            Transportation transportation = transportationRepository.findByEndPoint(transportationDto.getEndPoint()).get();

            client.setCompany(transportCompany);

            if (client.getTransportations() == null) {
                client.setTransportations(new HashSet<>());
            }

            client.getTransportations().add(transportation);
            clientRepository.save(client);

            return "Successfully registered client";
        }

        return "Client with this name already exists!";
    }

    @Override
    public String editClient(long clientId, ClientDto clientDto) {
        if (clientRepository.findById(clientId).isPresent()) {
            Client client = clientRepository.findById(clientId).get();

            mapper.map(clientDto, client);
            clientRepository.save(client);

            return "Successfully changed client's information.";
        }

        return "Cannot find a client with this id!";
    }

    @Override
    public String payTransportationPrice(long clientId, long transportationId) {
        Transportation transportation = transportationRepository.findById(transportationId).get();
        Client client = clientRepository.findById(clientId).get();

        if (client != null && transportation.getClient().getId() == clientId) {
            client.getTransportations().add(transportation);
            transportation.setPaid(true);

            clientRepository.save(client);
            transportationRepository.save(transportation);

            return "Successfully paid transportation price";
        }

        return "Unable to pay transportation: cannot find a client with this name/ wrong transportation!";
    }

    @Override
    public String sendPeople(long clientId, Set<PersonDto> peopleDtos, TransportationRequestDto transportationDto) {
        if (clientRepository.findById(clientId).isPresent()) {
            Client client = clientRepository.findById(clientId).get();

            // a transportation should have already been made
            Transportation transportation = transportationRepository.findByEndPoint(transportationDto.getEndPoint()).get();

            client.getTransportations().add(transportation);
            client.setCompany(transportation.getCompany());

            for (PersonDto personDto : peopleDtos) {
                //TODO: check vehicle capacity
//                if (transportation.getDriverEmployee().getVehicle().)) {
                    Person person = mapper.map(personDto, Person.class);

                    client.getLoad().add(person);
                    transportation.getLoad().add(person);

                    personRepository.save(person);
//                }
            }

            clientRepository.save(client);
            transportationRepository.save(transportation);

            return "Successfully registered people to be sent";
        }

        return "Cannot find a client with this id!";
    }

    @Override
    public String sendStock(long clientId, Set<StockDto> stocksDtos, TransportationRequestDto transportationDto) {
        if (clientRepository.findById(clientId).isPresent()) {
            Client client = clientRepository.findById(clientId).get();

            Transportation transportation = transportationRepository.findByEndPoint(transportationDto.getEndPoint()).get();

            client.getTransportations().add(transportation);
            client.setCompany(transportation.getCompany());

            for (StockDto stockDto : stocksDtos) {
                //TODO: check vehicle capacity
//                if (transportation.getDriverEmployee().getVehicle().)){
                    Stock stock = mapper.map(stockDto, Stock.class);

                    client.getLoad().add(stock);
                    transportation.getLoad().add(stock);

                    stockRepository.save(stock);
//                }
            }

            clientRepository.save(client);
            transportationRepository.save(transportation);

            return "Successfully registered stock to be sent";
        }

        return "Cannot find a client with this id!";
    }


    @Override
    public String deleteClient(long clientId) {
        if (clientRepository.findById(clientId).isPresent()) {
            Client client = clientRepository.findById(clientId).get();
            clientRepository.delete(client);

            return "Successfully deleted client";
        }

        return "Cannot find a client with this id!";
    }
}
