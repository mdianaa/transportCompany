package com.example.transportcompany.services.impl;

import com.example.transportcompany.models.dtos.requests.TransportCompanyRequestDto;
import com.example.transportcompany.models.dtos.requests.TransportationRequestDto;
import com.example.transportcompany.models.dtos.responses.TransportCompanyResponseDto;
import com.example.transportcompany.models.dtos.responses.TransportationResponseDto;
import com.example.transportcompany.models.entities.Income;
import com.example.transportcompany.repositories.IncomeRepository;
import com.example.transportcompany.services.IncomeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final ModelMapper mapper;

    public IncomeServiceImpl(IncomeRepository incomeRepository, ModelMapper mapper) {
        this.incomeRepository = incomeRepository;
        this.mapper = mapper;
    }

    @Override
    public BigDecimal calculateIncomeByMonth(TransportCompanyRequestDto transportCompany, Month month) {
        Income income = incomeRepository.findByMonthAndCompany(month, transportCompany).get();

        if (income.getCurrentMonthIncome() != null) {
            return income.getCurrentMonthIncome();
        }

        TransportCompanyResponseDto transportCompanyResponseDto = mapper.map(transportCompany, TransportCompanyResponseDto.class);

        BigDecimal incomeByMonth = new BigDecimal(0);
        for (TransportationRequestDto transportation : transportCompanyResponseDto.getTransportations()) {
            if (transportation.getDepartureDate().getMonth().equals(month)) {
                TransportationResponseDto transportationResponseDto = mapper.map(transportation, TransportationResponseDto.class);

                BigDecimal price = transportation.getTransportationPricePerUnit().multiply(BigDecimal.valueOf(transportationResponseDto.getLoad().size()));
                incomeByMonth = incomeByMonth.add(price);
            }
        }

        income.setCurrentMonthIncome(incomeByMonth);
        incomeRepository.saveAndFlush(income);

        return incomeByMonth;
    }
}
