package de.hey_car.controllers;

import de.hey_car.dto.Wallet;
import de.hey_car.services.WalletService;
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
@RequestMapping("/api/wallet")
public class WalletController {
    private static final Logger LOGGER = LogManager.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    /**
     * Method to
     */
    @PostMapping(value = "/create")
    public ResponseEntity<String> createWallet(@RequestBody @Valid Wallet wallet) {
        LOGGER.info("Processing create wallet ");
        walletService.createWallet(wallet);
        return ResponseEntity.ok().body(" published successfully ");
    }

    /**
     * Method to
     */
    @GetMapping(value = "/{otp}/{id}/confirm")
    public ResponseEntity<String> confirmEmail(@PathVariable String otp, @PathVariable String id) {
        LOGGER.info("Confirming the otp ");

        String response = walletService.confirmOtp(id, otp);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable String id) throws Exception {
        LOGGER.info("get wallet details ");

         walletService.getWallet(id);
        return ResponseEntity.ok().body("response");
    }
}
