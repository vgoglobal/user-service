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
public class Recipient {
    @NotNull
    private String name;
    @NotNull
    private String number;
    private String code;
    @NotNull
    private String currency;
    @NotNull
    private String institution;
    @NotNull
    private String institutionType;
    private String address;
    @NotNull
    private String userId;
}