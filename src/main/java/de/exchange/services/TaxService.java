package de.exchange.services;

import de.exchange.dto.Tax;
import de.exchange.entity.TaxEntity;

import java.util.List;

public interface TaxService {
    TaxEntity createTax(Tax tax);
    TaxEntity updateTax(Tax tax, String id);
    List<TaxEntity> getAll();
    TaxEntity getTaxByCurrency(String currency);
}
