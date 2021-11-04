package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Miner {
    private MinerResourceType resourceType;
    private String resourceName;
    private String resourceNumber;
    private String resourceCode;
    private String resourceAddress;
    private String resourceCurrency;
    private String userId;
}