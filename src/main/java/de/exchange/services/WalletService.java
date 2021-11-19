package de.exchange.services;

import de.exchange.dto.WalletDetails;
import de.exchange.dto.Wallet;
import de.exchange.dto.WalletResponse;
import de.exchange.entity.WalletEntity;

public interface WalletService {
    WalletEntity createWallet(Wallet wallet);
    String confirmOtp(String id, String code);
    WalletResponse getWallet(String id) throws Exception;
    void topUpWallet(WalletDetails walletDetails, String userId);
}
