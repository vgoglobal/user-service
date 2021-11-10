package de.hey_car.services.impl;

import de.hey_car.dto.Miner;
import de.hey_car.repository.MinerRepository;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.services.MinerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MinerServiceImpl implements MinerService {
    @Autowired
    MinerRepository minerRepository;

    @Override
    public MinerEntity createResource(Miner miner) {
        return minerRepository.save(inbound(miner));
    }

    @Override
    public MinerEntity getResource(String id) {
        return minerRepository.findById(id).orElseThrow();
    }

    @Override
    public List<MinerEntity> getAll() {
        return minerRepository.findAll();
    }

    private MinerEntity inbound(Miner miner) {
        return MinerEntity.builder().resourceType(miner.getResourceType())
                .resourceAddress(miner.getResourceAddress())
                .resourceCode(miner.getResourceCode())
                .resourceCurrency(miner.getResourceCurrency())
                .resourceName(miner.getResourceName())
                .resourceNumber(miner.getResourceNumber()).userId(miner.getUserId()).build();
    }
}
