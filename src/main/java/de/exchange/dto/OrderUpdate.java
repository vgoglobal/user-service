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
public class OrderUpdate {
    @NotNull
    private String userId;
    @NotNull
    private String targetMinerId;
    @NotNull
    private String sourceMinerId;
    @NotNull
    private String orderId;
    @NotNull
    private TransferStatusType status;
    private String remarks;
}