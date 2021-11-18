package de.hey_car.controllers;

import de.hey_car.dto.CountryWallet;
import de.hey_car.dto.Wallet;
import de.hey_car.dto.WalletResponse;
import de.hey_car.entity.WalletEntity;
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
     *
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<WalletEntity> createWallet(@RequestBody @Valid Wallet wallet) {
        LOGGER.info("Processing create wallet ");
        return ResponseEntity.ok().body(walletService.createWallet(wallet));
    }

    /**
     * Method to
     */
    @PostMapping(value = "/top-up")
    public ResponseEntity<String> topUpWallet(@RequestBody @Valid CountryWallet countryWallet) {
        LOGGER.info("Processing create wallet balance");
        walletService.topUpWallet(countryWallet);
        return ResponseEntity.ok().body(" published successfully ");
    }

    /**
     * Method to
     */
    @PostMapping(value = "/{otp}/{id}/confirm")
    public ResponseEntity<String> confirmEmail(@PathVariable String otp, @PathVariable String id) {
        LOGGER.info("Confirming the otp ");

        String response = walletService.confirmOtp(id, otp);
        return ResponseEntity.ok().body(response);
    }

    // TODO currency exhange fix, wallet join with country wallet
    @GetMapping(value = "/{id}/balance")
    public ResponseEntity<WalletResponse> getAccountBalance(@PathVariable String id) throws Exception {
        LOGGER.info("get wallet details ");
        return ResponseEntity.ok().body(walletService.getWallet(id));
    }
}
