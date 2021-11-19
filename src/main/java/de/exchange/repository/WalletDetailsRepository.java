package de.exchange.repository;

import de.exchange.entity.WalletDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletDetailsRepository extends CrudRepository<WalletDetailsEntity, String> {
    Optional<WalletDetailsEntity> findByWalletId(String id);
}
