package de.hey_car.repository;

import de.hey_car.entity.CountryWalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryWalletRepository extends CrudRepository<CountryWalletEntity, String> {

}
