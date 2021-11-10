package de.hey_car.services.impl;

import de.hey_car.dto.Currency;
import de.hey_car.repository.CurrencyRepository;
import de.hey_car.repository.entity.CurrencyEntity;
import de.hey_car.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public CurrencyEntity createExchange(Currency currency) {
        return currencyRepository.save(inbound(currency));
    }

    @Override
    public CurrencyEntity getExchange(String currencyCode) {
        return currencyRepository.findByFromCurrency(currencyCode).orElseThrow();
    }

    @Override
    public List<CurrencyEntity> getAll() {
        return currencyRepository.findAll();
    }

    private CurrencyEntity inbound(Currency currency) {
        return CurrencyEntity.builder()
                .fromCurrency(currency.getFromCurrency())
                .toCurrency(currency.getToCurrency())
                .amount(currency.getValue()).build();
    }

}
