package de.exchange.services;

import de.exchange.dto.Currency;
import de.exchange.entity.CurrencyEntity;

import java.util.List;

public interface CurrencyService {
    CurrencyEntity createExchange(Currency currency);
    CurrencyEntity getExchange(String fromCurrency, String toCurrency);
    List<CurrencyEntity> getAll();
}
