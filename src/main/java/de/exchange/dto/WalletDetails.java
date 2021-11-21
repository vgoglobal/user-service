package de.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDetails {
    private String currency;
    private Double amount;
    private String walletId;
    private Boolean baseCurrency;
    private ResourceType resourceType;
}