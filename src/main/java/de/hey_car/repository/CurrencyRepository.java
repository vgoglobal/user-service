package de.hey_car.repository;

import de.hey_car.repository.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
    Optional<CurrencyEntity> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
