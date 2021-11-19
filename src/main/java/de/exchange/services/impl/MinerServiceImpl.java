package de.exchange.services.impl;

import de.exchange.dto.Miner;
import de.exchange.dto.MinerAgency;
import de.exchange.entity.MinerAgencyEntity;
import de.exchange.repository.MinerRepository;
import de.exchange.entity.MinerEntity;
import de.exchange.services.MinerService;
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
                .minerAgency(buildMinerAgency(miner.getMinerAgency()))
                .resourceCurrency(miner.getResourceCurrency())
                .resourceName(miner.getResourceName())
                .resourceNumber(miner.getResourceNumber()).userId(miner.getUserId()).build();
    }

    private MinerAgencyEntity buildMinerAgency(MinerAgency minerAgency) {
        if (minerAgency != null) {
            return MinerAgencyEntity.builder().name(minerAgency.getName()).address(minerAgency.getAddress()).build();
        } else {
            return null;
        }
    }
}
