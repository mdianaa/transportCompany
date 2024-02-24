package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "load_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "loads")
public abstract class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="transportation_id")
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
