package com.example.transportcompany.models.entities;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "transport_companies")
public class Company extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String email;

    @OneToMany(mappedBy = "company", targetEntity = DriverEmployee.class)
    private Set<DriverEmployee> driverEmployees;

    @OneToMany(mappedBy = "company", targetEntity = Transportation.class)
    private Set<Transportation> transportations;

    @OneToMany(mappedBy = "company", targetEntity = Client.class)
    private Set<Client> clients;

    @OneToMany(mappedBy = "company", targetEntity = Income.class)
    private Set<Income> monthlyIncomes;
}
