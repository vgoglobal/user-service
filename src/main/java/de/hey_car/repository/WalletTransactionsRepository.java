package de.hey_car.repository;

import de.hey_car.repository.entity.WalletEntity;
import de.hey_car.repository.entity.WalletTransactionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletTransactionsRepository extends CrudRepository<WalletTransactionsEntity, String> {
    Optional<WalletEntity> findByUserId(String userId);
}
