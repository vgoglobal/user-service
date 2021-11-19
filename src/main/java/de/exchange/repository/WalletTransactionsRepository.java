package de.exchange.repository;

import de.exchange.entity.WalletTransactionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionsRepository extends CrudRepository<WalletTransactionsEntity, String> {
    //Optional<WalletEntity> findByUserId(String userId);
}
