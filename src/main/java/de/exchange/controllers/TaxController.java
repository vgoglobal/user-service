package de.exchange.controllers;

import de.exchange.dto.Tax;
import de.exchange.entity.TaxEntity;
import de.exchange.services.TaxService;
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
@RequestMapping("/api/tax")
public class TaxController {
    private static final Logger LOGGER = LogManager.getLogger(TaxController.class);

    @Autowired
    private TaxService taxService;

    /**
     * Method to
     *
     * @return
     */
    @PostMapping(value = "/")
    public ResponseEntity<TaxEntity> createTax(@RequestBody @Valid Tax tax) {
        LOGGER.info("Processing create tax ");
        return ResponseEntity.ok().body(taxService.createTax(tax));
    }

    /**
     * Method to
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaxEntity> updateComplaint(@RequestBody @Valid Tax tax, @PathVariable String id) {
        LOGGER.info("update tax processing ");
        return ResponseEntity.ok().body(taxService.updateTax(tax, id));
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<TaxEntity>> getAll() {
        LOGGER.info("get all tax ");
        return ResponseEntity.ok().body(taxService.getAll());
    }

    /**
     * Method to
     *
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaxEntity> getById(@PathVariable String id) {
        LOGGER.info("get tax by id");
        return ResponseEntity.ok().body(taxService.getTaxByCurrency(id));
    }
}
