package com.example.transportcompany.services;

import com.example.transportcompany.models.dtos.requests.CompanyRequestDto;

import java.math.BigDecimal;
import java.time.Month;

public interface IncomeService {

    BigDecimal calculateIncomeForCompanyByMonth(CompanyRequestDto transportCompany, Month month);
}
