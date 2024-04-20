package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Companies")
public class Company extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    @Column(nullable = false, length = 30)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "company", targetEntity = DriverEmployee.class, fetch = FetchType.EAGER)
    private Set<DriverEmployee> driverEmployees;

    @OneToMany(mappedBy = "company", targetEntity = Transportation.class, fetch = FetchType.EAGER)
    private Set<Transportation> transportations;

    @OneToMany(mappedBy = "company", targetEntity = Client.class, fetch = FetchType.EAGER)
    private Set<Client> clients;

    @OneToMany(mappedBy = "company", targetEntity = Vehicle.class, fetch = FetchType.EAGER)
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "company", targetEntity = Income.class, fetch = FetchType.EAGER)
    private Set<Income> monthlyIncomes;
}
