package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDriverVehicleDetailsDTO {
    private String verification;
    private String plateNo;
    private String color;
    private String registerNo;
    private String manufactureYear;
}
