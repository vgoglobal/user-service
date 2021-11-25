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
import java.util.Optional;

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

    @Override
    public List<MinerEntity> getResourceByCurrency(String currency) {
        return minerRepository.findByResourceCurrency(currency);
    }

    @Override
    public MinerEntity updateResource(Miner miner, String id) {
        Optional<MinerEntity> minerEntity = minerRepository.findById(id);
        if (minerEntity.isPresent()) {
            MinerEntity newMinerEntity = inbound(miner);
            newMinerEntity.setId(id);
            return minerRepository.save(newMinerEntity);
        } else {
            return null;
        }
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
