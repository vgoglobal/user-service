package de.hey_car.services;

import de.hey_car.dto.Currency;
import de.hey_car.dto.Miner;
import de.hey_car.repository.entity.CurrencyEntity;
import de.hey_car.repository.entity.MinerEntity;

import java.util.List;

public interface CurrencyService {
    CurrencyEntity createExchange(Currency currency);
    CurrencyEntity getExchange(String currencyCode);
    List<CurrencyEntity> getAll();
}
