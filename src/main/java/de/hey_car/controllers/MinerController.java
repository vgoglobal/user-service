package de.hey_car.controllers;

import de.hey_car.dto.Miner;
import de.hey_car.dto.User;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.UserEntity;
import de.hey_car.services.MinerService;
import de.hey_car.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
     */
    @PostMapping(value = "/resources/create")
    public ResponseEntity<String> createResource(@RequestBody @Valid Miner miner) {
        LOGGER.info("Processing create resource locations ");
        minerService.createResource(miner);
        return ResponseEntity.ok().body(" resource successfully created");
    }


    /**
     * Method to
     */
    @GetMapping(value = "/resources/{id}")
    public ResponseEntity<MinerEntity> getResource(@PathVariable String id) {
        return ResponseEntity.ok().body(minerService.getResource(id));
    }
}
