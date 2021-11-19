package de.exchange.services.impl;

import de.exchange.dto.Currency;
import de.exchange.repository.CurrencyRepository;
import de.exchange.entity.CurrencyEntity;
import de.exchange.services.CurrencyService;
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
