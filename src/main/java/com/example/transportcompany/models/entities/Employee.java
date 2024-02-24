package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.DriverQualification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private DriverQualification qualification;

    @OneToOne
    private Vehicle vehicle;

    @OneToMany(mappedBy = "employee")
    private Set<Transportation> transportations;

    @ManyToOne
    @JoinColumn(name="company_id")
    private TransportCompany company;
}
