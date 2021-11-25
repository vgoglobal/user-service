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
public class Complaint {
    private String orderId;
    private String userId;
    private String subject;
    private String comments;
    private String resolvedBy;
    private String status;
}