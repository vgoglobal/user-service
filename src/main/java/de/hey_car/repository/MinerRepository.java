package de.hey_car.repository;


import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinerRepository extends JpaRepository<MinerEntity, String> {
    List<MinerEntity> findByResourceCurrency(String currencyCode);
}