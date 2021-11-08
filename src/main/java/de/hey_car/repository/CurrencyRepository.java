package de.hey_car.repository;

import de.hey_car.repository.entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, String> {
    CurrencyEntity findByFromCurrency(String code);
}
