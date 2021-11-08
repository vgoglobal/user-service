package de.hey_car.controllers;

import de.hey_car.dto.Recipient;
import de.hey_car.dto.User;
import de.hey_car.repository.entity.RecipientEntity;
import de.hey_car.repository.entity.UserEntity;
import de.hey_car.services.RecipientService;
import de.hey_car.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Receiver;
import javax.validation.Valid;
import java.util.Optional;

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
     */
    @PostMapping(value = "/create")
    public ResponseEntity<String> createRecipient(@RequestBody @Valid Recipient recipient) {
        LOGGER.info("Processing create recipient ");
        recipientService.createRecipient(recipient);
        return ResponseEntity.ok().body(" recipient successfully created ");
    }

    /**
     * Method to
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<RecipientEntity> getRecipient(@PathVariable String userId) {
        LOGGER.info("Confirming the email ");

        recipientService.getRecipients(userId);
        return ResponseEntity.ok().body(recipientService.getRecipients(userId));
    }
}
