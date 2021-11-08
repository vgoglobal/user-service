package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Double amount;
    private Double recipientAmount;
    private Double transferFee;
    private String recipientId;
    private String fromCurrency;
    private String toCurrency;
    private String refId;
    private String userId;
    private String pickedBy;
}