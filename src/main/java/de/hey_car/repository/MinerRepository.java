package de.hey_car.repository;


import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MinerRepository extends CrudRepository<MinerEntity, String> {

}
