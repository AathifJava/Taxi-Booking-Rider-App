package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderEndTripDTO {
    private String verification;
    private Double trip_distance;
    private String end_lat;
    private String end_lot;
}
