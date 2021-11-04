package de.hey_car.services;

import de.hey_car.dto.Wallet;
import de.hey_car.repository.entity.WalletEntity;

public interface WalletService {
    WalletEntity createWallet(Wallet wallet);
    String confirmOtp(String id, String code);
    Wallet getWallet(String id) throws Exception;
}
