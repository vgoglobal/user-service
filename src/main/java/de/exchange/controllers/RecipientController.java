package de.exchange.controllers;

import de.exchange.dto.Recipient;
import de.exchange.entity.RecipientEntity;
import de.exchange.services.RecipientService;
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
@RequestMapping("/api/recipient")
public class RecipientController {
    private static final Logger LOGGER = LogManager.getLogger(RecipientController.class);

    @Autowired
    private RecipientService recipientService;

    /**
     * Method to
     * @return
     */
    @PostMapping(value = "/")
    public ResponseEntity<RecipientEntity> createRecipient(@RequestBody @Valid Recipient recipient) {
        LOGGER.info("Processing create recipient ");

        return ResponseEntity.ok().body(recipientService.createRecipient(recipient));
    }

    /**
     * Method to
     */
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<RecipientEntity> getRecipient(@PathVariable String userId) {
        LOGGER.info("Confirming the email ");
        return ResponseEntity.ok().body(recipientService.getRecipients(userId));
    }

    /**
     * Method to
     * @return
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<RecipientEntity>> getAll() {
        LOGGER.info("Confirming the email ");
        return ResponseEntity.ok().body(recipientService.getAll());
    }
}
