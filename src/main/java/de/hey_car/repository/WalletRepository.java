package de.hey_car.repository;

import de.hey_car.repository.entity.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, String> {
    Optional<WalletEntity> findByUserId(String userId);
}
