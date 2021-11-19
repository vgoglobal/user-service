package de.exchange.dto;

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
public class Wallet {
    private Long mobile; //TODO need to change it to wallet number
    @NotNull
    private String userId;
    @JsonIgnore
    private List<WalletDetails> walletDetails;
}