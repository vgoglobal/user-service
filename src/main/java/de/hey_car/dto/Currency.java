package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    private String fromCurrency;
    private String toCurrency;
    private Double value;
}