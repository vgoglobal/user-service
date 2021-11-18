package de.hey_car.repository;

import de.hey_car.entity.WalletTransactionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionsRepository extends CrudRepository<WalletTransactionsEntity, String> {
    //Optional<WalletEntity> findByUserId(String userId);
}
