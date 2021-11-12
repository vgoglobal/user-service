package de.hey_car.services;

import de.hey_car.dto.Currency;
import de.hey_car.repository.entity.CurrencyEntity;

import java.util.List;

public interface CurrencyService {
    CurrencyEntity createExchange(Currency currency);
    CurrencyEntity getExchange(String fromCurrency, String toCurrency);
    List<CurrencyEntity> getAll();
}
