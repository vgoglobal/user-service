package de.hey_car.services;

import de.hey_car.dto.Miner;
import de.hey_car.entity.MinerEntity;

import java.util.List;

public interface MinerService {
    MinerEntity createResource(Miner miner);
    MinerEntity getResource(String id);
    List<MinerEntity> getAll();
}
