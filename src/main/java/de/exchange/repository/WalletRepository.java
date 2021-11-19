package de.exchange.repository;

import de.exchange.entity.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, String> {
    Optional<WalletEntity> findByUserId(String userId);
}
