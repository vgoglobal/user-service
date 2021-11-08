package de.hey_car.controllers;

import de.hey_car.dto.User;
import de.hey_car.repository.entity.UserEntity;
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
@RequestMapping("/api/user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Method to
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid User user) {
        LOGGER.info("Processing create user ");
        //userService.createUser(user);
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    /**
     * Method to
     */
    @PutMapping(value = "/{confirmationCode}/{id}/confirm")
    public ResponseEntity<String> confirmEmail(@PathVariable String confirmationCode, @PathVariable String id) {
        LOGGER.info("Confirming the email ");

        userService.confirmEmail(id, confirmationCode);
        return ResponseEntity.ok().body(" Configuration of the email ");
    }

    /**
     * Method to
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<UserEntity>> getUsers(@PathVariable String id) {
        userService.getUser(id);
        return ResponseEntity.ok().body(userService.getUser(id));
    }
}
