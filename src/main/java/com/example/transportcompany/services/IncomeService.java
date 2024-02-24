package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;

import java.math.BigDecimal;
import java.time.Month;

public interface IncomeService {
    BigDecimal calculateIncomeByMonth(TransportCompanyRequestDto transportCompany, Month month);
}
