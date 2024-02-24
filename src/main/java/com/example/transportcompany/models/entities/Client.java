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
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, name = "phone_number", length = 20)
    private String phoneNumber;

    @OneToMany(mappedBy = "client")
    private Set<Transportation> transportations;

    @OneToMany(mappedBy = "client")
    private Set<Load> load;

    @ManyToOne
    @JoinColumn(name="company_id")
    private TransportCompany company;
}
