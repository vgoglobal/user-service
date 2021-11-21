package de.exchange.services;

import de.exchange.dto.Miner;
import de.exchange.entity.MinerEntity;

import java.util.List;

public interface MinerService {
    MinerEntity createResource(Miner miner);
    MinerEntity getResource(String id);
    List<MinerEntity> getAll();
    List<MinerEntity> getResourceByCurrency(String currency);
}
