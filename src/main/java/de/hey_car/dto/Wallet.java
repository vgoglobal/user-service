package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private String id;
    private Long mobile;
    private List<CountryWallet> countryWallet;
    private String otp;
    private Boolean otpConfirmed;
    private String userId;
    private Instant createDate;
    private Instant updatedDate;
}