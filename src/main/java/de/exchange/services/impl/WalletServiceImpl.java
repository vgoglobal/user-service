package de.exchange.services.impl;

import de.exchange.dto.WalletDetails;
import de.exchange.dto.Wallet;
import de.exchange.dto.WalletResponse;
import de.exchange.entity.WalletTransactionsEntity;
import de.exchange.repository.WalletDetailsRepository;
import de.exchange.repository.WalletRepository;
import de.exchange.entity.WalletDetailsEntity;
import de.exchange.entity.WalletEntity;
import de.exchange.repository.WalletTransactionsRepository;
import de.exchange.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletDetailsRepository walletDetailsRepository;

    @Autowired
    WalletTransactionsRepository walletTransactionsRepository;

    @Override
    public WalletEntity createWallet(Wallet wallet) {
        WalletEntity walletEntity = walletRepository.save(inbound(wallet));
        return walletEntity;
    }

    @Override
    public String confirmOtp(String id, String otp) {
        Optional<WalletEntity> walletEntity = walletRepository.findByUserId(id);
        walletEntity.filter(p -> otp.equals(p.getOtp())).orElseThrow();

        WalletEntity newWalletEntity = walletEntity.get();
        Duration duration = Duration.between(newWalletEntity.getUpdateDate(), Instant.now());
        if (duration.toMinutes() <= 5) {
            newWalletEntity.setOtpConfirmed(true);
            walletRepository.save(newWalletEntity);
            return "Otp confirmed";
        } else {
            generateOtp(newWalletEntity);
            walletRepository.save(newWalletEntity);
            return "Otp expired, generated new otp, please validate again";
        }
    }

    @Override
    public WalletResponse getWallet(String id) throws Exception {
        Optional<WalletEntity> walletEntity = walletRepository.findById(id);
        if (walletEntity.isPresent()) {
            return outbound(walletEntity.get());
        } else {
            throw new Exception(("Invalid id"));
        }
    }

    @Override
    public void topUpWallet(WalletDetails walletDetails, String userId) {
        Optional<WalletEntity> walletEntity = walletRepository.findByUserId(userId);
        if (walletEntity.isPresent()) {
            String id = walletEntity.get().getId();
            Optional<WalletDetailsEntity> walletDetailsEntity = walletDetailsRepository.findByWalletId(id);
            if (walletDetailsEntity.isPresent()) {
                WalletDetailsEntity walletDetailsEntity1 = walletDetailsEntity.get();
                Double balance = walletDetailsEntity1.getAmount() + walletDetails.getAmount();
                walletDetailsEntity1.setAmount(balance);
                walletDetailsRepository.save(walletDetailsEntity1);
                walletTransactionsRepository.save(WalletTransactionsEntity.builder().amount(walletDetails.getAmount()).walletId(id).build());
            } else {
                walletDetailsRepository.save(prepareCountryWallet(walletDetails));
                walletTransactionsRepository.save(WalletTransactionsEntity.builder().amount(walletDetails.getAmount()).walletId(id).build());
            }
        }
    }

    private WalletEntity inbound(Wallet wallet) {
        return WalletEntity.builder()
            .mobile(wallet.getMobile()).otp(generateOtp(wallet))
            .otpConfirmed(false)
            .userId(wallet.getUserId()).build();
    }

    private WalletResponse outbound(WalletEntity walletEntity) {
        return WalletResponse.builder()
            .mobile(walletEntity.getMobile())
            .userId(walletEntity.getUserId())
            .walletDetails(prepareCountryWallet(walletEntity))
            .walletId(walletEntity.getId())
            .build();
    }

    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }

    private String generateOtp(Wallet wallet) {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        return number + "";
    }

    private void generateOtp(WalletEntity wallet) {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        wallet.setOtp(number + "");
        wallet.setOtpConfirmed(false);
    }

    private List<WalletDetails> prepareCountryWallet(WalletEntity wallet) {
        return wallet.getWalletDetailsEntity().stream()
                .map(p -> WalletDetails.builder()
                        .amount(p.getAmount()).currency(p.getCurrency())
                        .build()).collect(Collectors.toList());

    }

    private WalletDetailsEntity prepareCountryWallet(WalletDetails walletDetails) {
        return WalletDetailsEntity.builder().walletId(walletDetails.getWalletId())
                .currency(walletDetails.getCurrency())
                .amount(walletDetails.getAmount()).build();

    }
}
