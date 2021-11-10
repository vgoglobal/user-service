package de.hey_car.services.impl;

import de.hey_car.dto.CountryWallet;
import de.hey_car.dto.Wallet;
import de.hey_car.repository.CountryWalletRepository;
import de.hey_car.repository.WalletRepository;
import de.hey_car.repository.entity.CountryWalletEntity;
import de.hey_car.repository.entity.WalletEntity;
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
    public Wallet getWallet(String id) throws Exception {
        Optional<WalletEntity> walletEntity = walletRepository.findByUserId(id);
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
        generateOtp(wallet);
        return WalletEntity.builder()
                .mobile(wallet.getMobile()).otp(wallet.getOtp())
                .otpConfirmed(wallet.getOtpConfirmed())
                .userId(wallet.getUserId()).build();
    }

    private Wallet outbound(WalletEntity walletEntity) {
        return Wallet.builder()
                .mobile(walletEntity.getMobile())
                .otp(walletEntity.getOtp())
                .otpConfirmed(walletEntity.getOtpConfirmed()).build();
    }

    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }

    private void generateOtp(Wallet wallet) {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        wallet.setOtp(number + "");
        wallet.setOtpConfirmed(false);
    }

    private void generateOtp(WalletEntity wallet) {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        wallet.setOtp(number + "");
        wallet.setOtpConfirmed(false);
    }

    /*private List<CountryWalletEntity> prepareCountryWallet(Wallet wallet) {
        return wallet.getCountryWallet().stream()
                .map(p -> CountryWalletEntity.builder()
                        .amount(p.getAmount()).currency(p.getCurrency())
                        .build()).collect(Collectors.toList());

    }*/

    private CountryWalletEntity prepareCountryWallet(CountryWallet countryWallet) {
        return CountryWalletEntity.builder().walletId(countryWallet.getWalletId())
                .currency(countryWallet.getCurrency())
                .amount(countryWallet.getAmount()).build();

    }
}
