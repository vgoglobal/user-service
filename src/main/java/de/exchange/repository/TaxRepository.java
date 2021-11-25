package de.exchange.repository;

import de.exchange.entity.ComplaintEntity;
import de.exchange.entity.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, String> {
    TaxEntity findByCurrency(String currency);
}
