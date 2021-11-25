package de.exchange.controllers;

import de.exchange.dto.Miner;
import de.exchange.entity.MinerEntity;
import de.exchange.services.MinerService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     *
     * @return
     */
    @PostMapping(value = "/")
    public ResponseEntity<MinerEntity> createMiner(@RequestBody @Valid Miner miner) {
        LOGGER.info("Processing create resource locations ");
        return ResponseEntity.ok().body(minerService.createResource(miner));
    }

    /**
     * Method to
     *
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<MinerEntity> updateMiner(@RequestBody @Valid Miner miner, @PathVariable(name = "id") String id) {
        LOGGER.info("Processing create resource locations ");
        return ResponseEntity.ok().body(minerService.createResource(miner));
    }

    /**
     * Method to
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<MinerEntity> getResource(@PathVariable String id) {
        return ResponseEntity.ok().body(minerService.getResource(id));
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<MinerEntity>> getAll() {
        return ResponseEntity.ok().body(minerService.getAll());
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/currency/{currency}")
    public ResponseEntity<List<MinerEntity>> getByCurrency(@PathVariable String currency) {
        return ResponseEntity.ok().body(minerService.getResourceByCurrency(currency));
    }
}
