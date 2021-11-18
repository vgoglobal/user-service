package de.hey_car.services;

import de.hey_car.dto.CountryWallet;
import de.hey_car.dto.Wallet;
import de.hey_car.dto.WalletResponse;
import de.hey_car.entity.WalletEntity;

public interface WalletService {
    WalletEntity createWallet(Wallet wallet);
    String confirmOtp(String id, String code);
    WalletResponse getWallet(String id) throws Exception;
    void topUpWallet(CountryWallet countryWallet);
}
