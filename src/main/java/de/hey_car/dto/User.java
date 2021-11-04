package de.hey_car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String address;
    private Long mobile;
}