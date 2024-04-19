package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {

    //TODO: registration number as ID
    @Column(nullable = false, name = "registration_number", length = 20)
    private String registrationNumber;

    @Column(nullable = false, length = 20)
    private String model;

    @Column(length = 10)
    private String engine;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "vehicle", targetEntity = DriverEmployee.class)
    private DriverEmployee driverEmployee;

}
