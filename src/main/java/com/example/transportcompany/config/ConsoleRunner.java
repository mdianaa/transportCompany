package com.example.transportcompany.config;

import com.example.transportcompany.models.dtos.requests.*;
import com.example.transportcompany.models.entities.DriverEmployee;
import com.example.transportcompany.models.entities.Company;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.services.*;
import com.example.transportcompany.utils.enums.CompanyType;
import com.example.transportcompany.utils.enums.DriverQualification;
import com.example.transportcompany.utils.enums.VehicleTypeForPeopleTransportation;
import com.example.transportcompany.utils.enums.VehicleTypeForStockTransportation;
import com.example.transportcompany.utils.fileLogic.TransportationFileLogic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final CompanyService companyService;
    private final TransportationService transportationService;
    private final ClientService clientService;
    private final DriverEmployeeService driverEmployeeService;
    private final IncomeService incomeService;
    private final PersonVehicleService personVehicleService;
    private final StockVehicleService stockVehicleService;

    public ConsoleRunner(CompanyService companyService, TransportationService transportationService, ClientService clientService, DriverEmployeeService driverEmployeeService, IncomeService incomeService, PersonVehicleService personVehicleService, StockVehicleService stockVehicleService) {
        this.companyService = companyService;
        this.transportationService = transportationService;
        this.clientService = clientService;
        this.driverEmployeeService = driverEmployeeService;
        this.incomeService = incomeService;
        this.personVehicleService = personVehicleService;
        this.stockVehicleService = stockVehicleService;
    }

    @Override
    public void run(String... args) {

        // prepare different dtos and save them to the db

        CompanyRequestDto stockTransportCompany = new CompanyRequestDto("DHL OOD", CompanyType.STOCK_TRANSPORT_COMPANY, "Sofia, bul. Bulgaria", "0888234136", "dhl@gmail.com");
        CompanyRequestDto personTransportCompany = new CompanyRequestDto("Linda Transportation Special", CompanyType.PERSON_TRANSPORT_COMPANY, "Sofia, bul. G.M.Dimitrov", "0888252100", "linda.transport@gmail.com");

        companyService.registerCompany(stockTransportCompany);
        companyService.registerCompany(personTransportCompany);

        TransportationRequestDto transportation1 = new TransportationRequestDto("Plovdiv", "Varna", LocalDate.now(), LocalDate.of(2024, 5, 23), BigDecimal.valueOf(12));
        TransportationRequestDto transportation2 = new TransportationRequestDto("Blagoevgrad", "Sofia", LocalDate.of(2023, 4, 23), LocalDate.of(2024, 4, 23), BigDecimal.valueOf(10));

        transportationService.registerTransportation(transportation1, stockTransportCompany);
        transportationService.registerTransportation(transportation2, personTransportCompany);

        ClientDto client1 = new ClientDto("Trans OOODs", "0897453612", "trans.oods@gmail.com");
        ClientDto client2 = new ClientDto("Ivan Petroff", "089451623", "ivan.petroff@gmail.com");

        clientService.registerClient(client1, stockTransportCompany, transportation1);
        clientService.registerClient(client2, personTransportCompany, transportation2);

        StockTransportVehicleDto stockTransportVehicle = new StockTransportVehicleDto("236534", "StR", "V8", DriverQualification.C1E, VehicleTypeForStockTransportation.SMALL_TRUCK);
        PeopleTransportVehicleDto peopleTransportVehicle = new PeopleTransportVehicleDto("325760", "AmR", "V8", DriverQualification.D1E, VehicleTypeForPeopleTransportation.SMALL_BUS);

        stockVehicleService.registerStockVehicle(stockTransportVehicle, stockTransportCompany);
        personVehicleService.registerPersonVehicle(peopleTransportVehicle, personTransportCompany);

        EmployeeDto employee1 = new EmployeeDto("Petar Dimitrov", "089253657", "petar.stoyanov@gmail.com", BigDecimal.valueOf(6000), DriverQualification.C1E);
        EmployeeDto employee2 = new EmployeeDto("Ivaylo Stoyanov", "0888226615", "ivaylo.dimitrov@gmail.com", BigDecimal.valueOf(5200), DriverQualification.D1E);

        driverEmployeeService.registerEmployee(employee1, stockTransportCompany, stockVehicleService.getVehicleByRegistrationNumber("236534"));
        driverEmployeeService.registerEmployee(employee2, personTransportCompany, personVehicleService.getVehicleByRegistrationNumber("325760"));

        System.out.println(companyService.getTotalPriceOfAllTransportations(stockTransportCompany.getName()));
        System.out.println(companyService.getTotalPriceOfAllTransportations(personTransportCompany.getName()));

        // write in file
        List<Transportation> transportations = new ArrayList<>();
        transportations.add(transportationService.getTransportationByDestination("Varna"));
        transportations.add(transportationService.getTransportationByDestination("Sofia"));

//        TransportationFileLogic.writeTransportationsToFile(transportations);
//
//        // read from file
//        TransportationFileLogic.readTransportationsFromFile();

//        // sort companies by name
//        List<Company> companies = companyService.getAllCompanies();
//        companies.sort(Comparator.comparing(Company::getName));
//        // sort companies by income
//        // Collections.sort(companies, Comparator.comparingDouble(TransportCompany::getMonthlyIncomes));
//
//        // filter companies by name
//        String prefix = "Li";
//
//        List<Company> filteredCompaniesName = companies.stream()
//                .filter(c -> c.getName().startsWith(prefix))
//                .toList();
//
//        // filter companies by income
////         double income = 100000;
////         List<TransportCompany> filteredCompaniesIncome = companies.stream().filter(c -> c.getMonthlyIncomes() > income).toList();
//
//        List<DriverEmployee> driverEmployees = driverEmployeeService.getAllEmployeesInCompany(companyService.getCompanyByName(stockTransportCompany.getName()).getName());
//
//        // sort employees by qualification
//        driverEmployees.sort(Comparator.comparing(DriverEmployee::getQualification));
//
//        // sort employees by salary
//        driverEmployees.sort(Comparator.comparing(DriverEmployee::getSalary));
//
//        // filter employees by qualification
//        DriverQualification qualification = DriverQualification.C1E;
//        List<DriverEmployee> filteredEmployeesByQualification = driverEmployees.stream()
//                .filter(e -> e.getQualification().equals(qualification))
//                .toList();
//
//        // filter employees by salary
//        BigDecimal salary = BigDecimal.valueOf(10000);
//        List<DriverEmployee> filteredEmployeesBySalary = driverEmployees.stream()
//                .filter(e -> e.getSalary().compareTo(salary) > 0)
//                .toList();
//
//        transportations = transportationService.getAllTransportationsForCompany(stockTransportCompany.getName());
//
//        // sort transportations by destination
//
//        transportations.sort(Comparator.comparing(Transportation::getEndPoint));
//
//        // filter transportations by destination
//        String endPoint = "Sofia";
//
//        List<Transportation> filteredTransportsByEndPoint = transportations.stream()
//                .filter(t -> t.getEndPoint().equals(endPoint))
//                .toList();
//
//        BigDecimal income = incomeService.calculateIncomeForCompanyByMonth(stockTransportCompany, Month.FEBRUARY);
//        System.out.println(income);
    }
}
