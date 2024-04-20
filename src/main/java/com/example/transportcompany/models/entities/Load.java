package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "loads")
public class Load extends BaseEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToMany(mappedBy = "load", targetEntity = Transportation.class)
    private Set<Transportation> transportations;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
