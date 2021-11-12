package de.hey_car.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private Long mobile; //TODO need to change it to wallet number
    private String userId;
    private List<CountryWallet> countryWallet;
    private String walletId;
}