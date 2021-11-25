package de.exchange.services.impl;

import de.exchange.dto.Tax;
import de.exchange.entity.TaxEntity;
import de.exchange.repository.TaxRepository;
import de.exchange.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    TaxRepository complaintRepository;

    @Override
    public TaxEntity createTax(Tax tax) {
        return complaintRepository.save(inbound(tax));
    }

    @Override
    public TaxEntity updateTax(Tax tax, String id) {
        if (complaintRepository.findById(id).isPresent()) {
            return complaintRepository.save(inbound(tax));
        } else {
            return null;
        }
    }

    @Override
    public List<TaxEntity> getAll() {
        return complaintRepository.findAll();
    }

    @Override
    public TaxEntity getTaxByCurrency(String currency) {
        return complaintRepository.findByCurrency(currency);
    }

    private TaxEntity inbound(Tax tax) {
        return TaxEntity.builder().amountPercent(tax.getAmountPercent()).currency(tax.getCurrency()).amount(tax.getAmount()).build();
    }
}
