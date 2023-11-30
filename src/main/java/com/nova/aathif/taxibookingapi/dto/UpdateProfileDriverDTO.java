package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateProfileDriverDTO {
    private String verification;
    private String name;
    private String address;
    private String birthday;
    private String emergency_mobile;
    private String email;
    private int gender;
    private String lisence_number;
}
