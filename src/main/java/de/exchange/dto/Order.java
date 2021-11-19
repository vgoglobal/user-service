package de.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NotNull
    private Double amount;
    @NotNull
    private Double recipientAmount;
    @NotNull
    private Double transferFee;
    private String recipientId;
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;
    private String refId;
    @NotNull
    private String userId;
    private String pickedBy;
}