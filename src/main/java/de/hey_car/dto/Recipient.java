package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {
    private String name;
    private String number;
    private String code;
    private String currency;
    private String institution;
    private String institutionType;
    private String address;
    private String userId;
}