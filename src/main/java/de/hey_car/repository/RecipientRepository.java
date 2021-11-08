package de.hey_car.repository;

import de.hey_car.repository.entity.CurrencyEntity;
import de.hey_car.repository.entity.RecipientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends CrudRepository<RecipientEntity, String> {
    RecipientEntity findByUserId(String userId);
}
