package de.hey_car.services;

import de.hey_car.dto.Miner;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.UserEntity;

public interface MinerService {
    MinerEntity createResource(Miner miner);
    MinerEntity getResource(String id);
}
