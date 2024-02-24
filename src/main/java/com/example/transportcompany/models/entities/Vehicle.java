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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "vehicle_transport_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(nullable = false, name = "registration_number")
    private String registrationNumber;

    @Column(nullable = false)
    private String model;

    @Column()
    private String engine;

    @ManyToOne
    @JoinColumn(name="company_id")
    private TransportCompany company;

    @OneToOne(mappedBy="vehicle")
    private Employee employee;

}
