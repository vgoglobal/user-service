package de.hey_car.repository;

import de.hey_car.repository.entity.CountryWalletEntity;
import de.hey_car.repository.entity.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryWalletRepository extends CrudRepository<CountryWalletEntity, String> {

}
