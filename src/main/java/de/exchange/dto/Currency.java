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
public class Currency {
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;
    @NotNull
    private Double value;
}