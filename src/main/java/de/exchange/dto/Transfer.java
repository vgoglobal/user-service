package de.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private String orderId;
    private String refId;
    private ResourceType resourceType;
    private String resourceName;
    private String resourceNumber;
    private String resourceCode;
    private String resourceAddress;
    private String resourceCurrency;
    private Double transferAmount;
}