package de.exchange.dto;

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
public class Miner {
    @NotNull
    private ResourceType resourceType;
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
    private MinerAgency minerAgency;
    private List serviceType;// deposit, pickup, transfer
}