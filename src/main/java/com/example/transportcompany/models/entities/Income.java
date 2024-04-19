package com.example.transportcompany.models.entities;

import com.example.transportcompany.utils.enums.Month;
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
@Table(name = "income")
public class Income extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "current_mounth_income", nullable = false)
    private BigDecimal currentMonthIncome;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

}
