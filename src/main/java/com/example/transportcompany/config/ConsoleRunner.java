package com.example.transportcompany.config;

import com.example.transportcompany.models.dtos.requests.*;
import com.example.transportcompany.models.entities.Employee;
import com.example.transportcompany.models.entities.TransportCompany;
import com.example.transportcompany.models.entities.Transportation;
import com.example.transportcompany.services.*;
import com.example.transportcompany.utils.enums.DriverQualification;
import com.example.transportcompany.utils.enums.VehicleTypeTransportPeople;
import com.example.transportcompany.utils.enums.VehicleTypeTransportStock;
import com.example.transportcompany.utils.fileLogic.TransportationFileLogic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final TransportCompanyService transportCompanyService;
    private final TransportationService transportationService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final IncomeService incomeService;
    private final PersonVehicleService personVehicleService;
    private final StockVehicleService stockVehicleService;

    public ConsoleRunner(TransportCompanyService transportCompanyService, TransportationService transportationService, ClientService clientService, EmployeeService employeeService, IncomeService incomeService, PersonVehicleService personVehicleService, StockVehicleService stockVehicleService) {
        this.transportCompanyService = transportCompanyService;
        this.transportationService = transportationService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.incomeService = incomeService;
        this.personVehicleService = personVehicleService;
        this.stockVehicleService = stockVehicleService;
    }

    @Override
    public void run(String... args) {

        // prepare different dtos and save them to the db

        StockTransportCompanyRequestDto company1 = new StockTransportCompanyRequestDto("DHL OOD", "Sofia, bul. Bulgaria", "0888234136", "dhl@gmail.com");
        PeopleTransportCompanyRequestDto company2 = new PeopleTransportCompanyRequestDto("Linda Transportation Special", "Sofia, bul. G.M.Dimitrov", "0888252100", "linda.transport@gmail.com");

        transportCompanyService.registerCompany(company1);
        transportCompanyService.registerCompany(company2);

        TransportationRequestDto transportation1 = new TransportationRequestDto("Plovdiv", "Varna", LocalDate.now(), LocalDate.of(2024, 5, 23), BigDecimal.valueOf(12));
        TransportationRequestDto transportation2 = new TransportationRequestDto("Blagoevgrad", "Sofia", LocalDate.of(2023, 4, 23), LocalDate.of(2024, 4, 23), BigDecimal.valueOf(10));

        transportationService.registerTransportation(transportation1, company1);
        transportationService.registerTransportation(transportation2, company2);

        ClientDto client1 = new ClientDto("Trans OOODs", "0897453612", "trans.oods@gmail.com");
        ClientDto client2 = new ClientDto("Ivan Petroff", "089451623", "ivan.petroff@gmail.com");

        clientService.registerClient(client1, company1);
        clientService.registerClient(client2, company2);

        StockTransportVehicleDto stockTransportVehicle = new StockTransportVehicleDto("236534", "StR", "V8", VehicleTypeTransportStock.SMALL_TRUCK);
        PeopleTransportVehicleDto peopleTransportVehicle = new PeopleTransportVehicleDto("325760", "AmR", "V8", VehicleTypeTransportPeople.SMALL_BUS);

        stockVehicleService.registerStockVehicle(stockTransportVehicle, company1);
        personVehicleService.registerPersonVehicle(peopleTransportVehicle, company2);

        EmployeeDto employee1 = new EmployeeDto("Petar Dimitrovv", "089253657", "petar.stoyanovv@gmail.com", BigDecimal.valueOf(6000), DriverQualification.C1E);
        EmployeeDto employee2 = new EmployeeDto("Ivaylo Stoyanovv", "0888226615", "ivaylo.dimitrovv@gmail.com", BigDecimal.valueOf(5200), DriverQualification.D1E);

        employeeService.registerEmployee(employee1, company1, stockVehicleService.getVehicleByRegistrationNumber("236534"));
        employeeService.registerEmployee(employee2, company2, personVehicleService.getVehicleByRegistrationNumber("325760"));

        transportCompanyService.getTotalPriceOfAllTransportations(company1.getName());
        transportCompanyService.getTotalPriceOfAllTransportations(company2.getName());

        // write in file
        List<Transportation> transportations = new ArrayList<>();
        transportations.add(transportationService.getTransportationByDestination("Varna"));
        transportations.add(transportationService.getTransportationByDestination("Sofia"));

        TransportationFileLogic.writeTransportationsToFile(transportations);

        // read from file
        TransportationFileLogic.readTransportationsFromFile();

        // sort companies by name
        List<TransportCompany> companies = transportCompanyService.getAllCompanies();
        companies.sort(Comparator.comparing(TransportCompany::getName));
        // sort companies by income
        // Collections.sort(companies, Comparator.comparingDouble(TransportCompany::getMonthlyIncomes));

        // filter companies by name
        String prefix = "Li";

        List<TransportCompany> filteredCompaniesName = companies.stream()
                .filter(c -> c.getName().startsWith(prefix))
                .toList();

        // filter companies by income
//         double income = 100000;
//         List<TransportCompany> filteredCompaniesIncome = companies.stream().filter(c -> c.getMonthlyIncomes() > income).toList();

        List<Employee> employees = employeeService.getAllEmployeesInCompany(transportCompanyService.getCompanyByName(company1.getName()).getName());

        // sort employees by qualification
        employees.sort(Comparator.comparing(Employee::getQualification));

        // sort employees by salary
        employees.sort(Comparator.comparing(Employee::getSalary));

        // filter employees by qualification
        DriverQualification qualification = DriverQualification.C1E;
        List<Employee> filteredEmployeesByQualification = employees.stream()
                .filter(e -> e.getQualification().equals(qualification))
                .toList();

        // filter employees by salary
        BigDecimal salary = BigDecimal.valueOf(10000);
        List<Employee> filteredEmployeesBySalary = employees.stream()
                .filter(e -> e.getSalary().compareTo(salary) > 0)
                .toList();

        transportations = transportationService.getAllTransportations();

        // sort transportations by destination

        transportations.sort(Comparator.comparing(Transportation::getEndPoint));

        // filter transportations by destination
        String endPoint = "Sofia";

        List<Transportation> filteredTransportsByEndPoint = transportations.stream()
                .filter(t -> t.getEndPoint().equals(endPoint))
                .toList();

        BigDecimal income = incomeService.calculateIncomeByMonth(company1, Month.FEBRUARY);
        System.out.println(income);
    }
}
