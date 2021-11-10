package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miner {
    @NotNull
    private MinerResourceType resourceType;
    @NotNull
    private String resourceName;
    private String resourceNumber;
    @NotNull
    private String resourceCode;
    private String resourceAddress;
    @NotNull
    private String resourceCurrency;
    @NotNull
    private String userId;
}