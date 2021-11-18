package de.hey_car.services.impl;

import de.hey_car.dto.CountryWallet;
import de.hey_car.dto.Wallet;
import de.hey_car.dto.WalletResponse;
import de.hey_car.repository.CountryWalletRepository;
import de.hey_car.repository.WalletRepository;
import de.hey_car.entity.CountryWalletEntity;
import de.hey_car.entity.WalletEntity;
import de.hey_car.services.WalletService;
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
    CountryWalletRepository countryWalletRepository;

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
    public void topUpWallet(CountryWallet countryWallet) {
        countryWalletRepository.save(prepareCountryWallet(countryWallet));
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
                .countryWallet(prepareCountryWallet(walletEntity))
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

    private List<CountryWallet> prepareCountryWallet(WalletEntity wallet) {
        return wallet.getCountryWalletEntity().stream()
                .map(p -> CountryWallet.builder()
                        .amount(p.getAmount()).currency(p.getCurrency())
                        .build()).collect(Collectors.toList());

    }

    private CountryWalletEntity prepareCountryWallet(CountryWallet countryWallet) {
        return CountryWalletEntity.builder().walletId(countryWallet.getWalletId())
                .currency(countryWallet.getCurrency())
                .amount(countryWallet.getAmount()).build();

    }
}
