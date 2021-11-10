package de.hey_car.controllers;

import de.hey_car.dto.Miner;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.services.MinerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/miner")
public class MinerController {
    private static final Logger LOGGER = LogManager.getLogger(MinerController.class);

    @Autowired
    private MinerService minerService;

    /**
     * Method to
     * @return
     */
    @PostMapping(value = "/resources/create")
    public ResponseEntity<MinerEntity> createResource(@RequestBody @Valid Miner miner) {
        LOGGER.info("Processing create resource locations ");
        return ResponseEntity.ok().body(minerService.createResource(miner));
    }


    /**
     * Method to
     */
    @GetMapping(value = "/resources/{id}")
    public ResponseEntity<MinerEntity> getResource(@PathVariable String id) {
        return ResponseEntity.ok().body(minerService.getResource(id));
    }
}
