package com.nova.aathif.taxibookingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripHistoryByRiderDTO {
    private String verification;
    private String from;
}
