package de.hey_car.services.impl;

import de.hey_car.dto.Currency;
import de.hey_car.repository.CurrencyRepository;
import de.hey_car.repository.entity.CurrencyEntity;
import de.hey_car.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public CurrencyEntity createExchange(Currency currency) {
        Optional<CurrencyEntity> currencyEntity = currencyRepository.findByFromCurrencyAndToCurrency(currency.getFromCurrency(), currency.getToCurrency());
        if (currencyEntity.isPresent()) {
            CurrencyEntity newCurrency = currencyEntity.get();
            newCurrency.setAmount(currency.getValue());
            return currencyRepository.save(newCurrency);
        }
        return currencyRepository.save(inbound(currency));
    }

    @Override
    public CurrencyEntity getExchange(String fromCurrency, String toCurrency) {
        return currencyRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElseThrow();
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
