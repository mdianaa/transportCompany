package com.example.transportcompany.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
//@DiscriminatorValue("STOCK_LOAD")
public class Stock extends Load {

    @Column(nullable = false)
    private BigDecimal weight;
}
