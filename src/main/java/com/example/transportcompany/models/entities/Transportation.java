package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transportations")
public class Transportation extends BaseEntity {

    @Column(name = "start_point", nullable = false, length = 30)
    private String startPoint;

    @Column(name = "end_point", nullable = false, length = 30)
    private String endPoint;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "transportation_price_per_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal transportationPricePerUnit;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private DriverEmployee driverEmployee;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Load> load;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;
}
