package de.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    private Long mobile; //TODO need to change it to wallet number
    private String userId;
    private List<WalletDetails> walletDetails;
    private String walletId;
}