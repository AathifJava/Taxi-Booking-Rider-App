package com.nova.aathif.taxibookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderAcceptTripDTO {
//    public String verification;
    public int vehicle_type_id;
    public int trip_id;
    public String verification;
}
