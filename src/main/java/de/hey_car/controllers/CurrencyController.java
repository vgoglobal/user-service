package de.hey_car.controllers;

import de.hey_car.dto.Currency;
import de.hey_car.dto.Miner;
import de.hey_car.repository.entity.CurrencyEntity;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.services.CurrencyService;
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
@RequestMapping("/api/currency")
public class CurrencyController {
    private static final Logger LOGGER = LogManager.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;

    /**
     * Method to
     */
    @PostMapping(value = "/create")
    public ResponseEntity<String> createResource(@RequestBody @Valid Currency currency) {
        LOGGER.info("Processing create resource locations ");
        currencyService.createExchange(currency);
        return ResponseEntity.ok().body(" resource successfully created");
    }


    /**
     * Method to
     */
    @GetMapping(value = "/{code}")
    public ResponseEntity<CurrencyEntity> getResource(@PathVariable String code) {
        return ResponseEntity.ok().body(currencyService.getExchange(code));
    }
}
